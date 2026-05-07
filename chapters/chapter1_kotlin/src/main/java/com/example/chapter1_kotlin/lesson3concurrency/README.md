## Lesson 3 并发问题
| 集美底子好vibe不怕的
### 1 涉及的问题及其处理思路
* **生产-消费者模型**
  * 问题：生产者往消费队列中生产内容，消费者从消费队列中拿走并消费内容
  * 思路：使用带缓冲容量的 `Channel` 模拟仓库（缓冲池）。生产者通过 `channel.send()` 挂起式地放入商品（满则等待），消费者通过 `channel.receive()` 挂起式地消费商品（空则等待）。使用 `Mutex` 来保护和同步当前生产/消费数量的计数器。
* **读者-写者问题**
  * 问题：有若干读者若干写者共享一个文件，可以多人一起读，但只能一个人写
  * 思路：允许多个读者同时阅读，但写者必须独占。引入 `readMutex` 和 `writeMutex`。读者进入时，第一个读者负责获取 `writeMutex`（锁住写者），随后读者共享资源；最后一个离开的读者释放 `writeMutex`。写者直接请求 `writeMutex`，保证了写操作的互斥性。
* **哲学家就餐问题**
  * 问题：哲学家有思考和吃饭两个动作，吃饭需要拿左右两根筷子才能吃，n个人一共有n根筷子
  * 思路：使用 `Mutex` 数组代表桌上的筷子。为了避免所有哲学家同时拿起左手边筷子而导致的**死锁**问题，采用了**非对称策略**：规定最后一个哲学家先拿右手边的筷子，再拿左手边的筷子，打破了循环等待条件。
* **理发师睡眠问题**
  * 问题：有人来理发师就理发，没人来就睡觉，来的人需要排队等位
  * 思路：同样使用带容量的 `Channel` 模拟等候区的椅子。顾客到来时，使用 `channel.trySend()` 尝试坐下，如果成功则等待理发，如果失败（缓冲区满）则直接离开。理发师通过 `channel.receive()` 获取顾客，如果没有顾客则自然挂起（模拟睡觉）。
* **吸烟者问题**
  * 三个人各自缺的材料不同，每次桌子上会放上一种材料，可以凑齐的人拿走
  * 思路：使用 `Channel` 模拟桌子。供应者随机提供两种材料，拥有第三种材料的吸烟者被唤醒并拿走材料抽烟。抽完后通过 `agentDone` Channel 发送信号通知供应者继续下一轮。
* **交替执行场景**
  * 问题：两个线程/协程交替执行任务
  * 思路：使用两个容量为 1 的 `Channel` (`turnA`, `turnB`) 作为信号量，利用其“缓冲区满则挂起”的特性，实现两个协程像打乒乓球一样的相互唤醒与交替执行。

### 2 Presenter 实现
* **BaseConcurrencyPresenter**
    * 作为所有并发演示的基类，内部维护了一个绑定生命周期的 `demoScope` (`CoroutineScope` + `SupervisorJob`)。
    * 使用 `MutableSharedFlow<String>` 作为日志流 (`logFlow`)，将算法执行过程中的信息以非阻塞的形式发射给外层。
    * 统一封装了 `startDemonstration()` 和 `stopDemonstration()`，在每次重新开始前自动取消 (`cancel`) 上一次的协程 Job，防止协程泄漏和互相干扰。
* **子类 Presenter**
    * 不同的并发模型（如 `ProducerConsumerPresenter` 等）继承基类，通过重写 `runAlgorithm(params: Map<String, Int>)`来实现差异化的展示

### 3 Fragment 构造
* **Lesson3ConcurrencyActivity**
    * 作为容器页面，通过读取 `ConcurrencyType` 枚举，动态生成对应的 Tab 页与 Fragment 加载到 `viewpager2` 中。
* **ConcurrencyFragment**
    * 通过工厂方法 `newInstance(type)` 接收枚举类型。在 `onCreate` 时，利用多态根据类型实例化对应的 `Presenter` 子类。
    * 利用 Kotlin 的 `lifecycleScope.launch` 和 `repeatOnLifecycle` 在安全的生命周期内收集 (`collect`) Presenter 吐出的日志流，更新至控制台 UI 中。
    * 在 `onDestroyView` 时调用 `presenter.stopDemonstration()`，保证页面切走时及时终止后台并发计算。

### 4 参数导入与结果呈现
* **参数导入 (ParamInjectUtil)**
    * 在 `CorountineEntity.kt` 中，为每种枚举模型定义了默认参数列表（`ParamConfig`，如生产者数量、目标数等）。
    * 封装了 `ParamInjectUtil` 工具类。在 View 初始化时，工具类根据当前模型的参数配置列表，动态 `inflate` 输入框 (`item_param_input.xml`) 到界面的容器中，并返回一个 `Map<String, EditText>`。
    * 点击“开始”按钮时，Fragment 遍历 Map 快速收集最新的填入值，并转换为 Int 传入 Presenter 中。
* **结果呈现 (Console Log)**
    * TODO: 这里后续会改成rcv或者其他样式
    * 提供了一个可滚动的 `ScrollView` 包裹 `TextView` (`tvConsoleLog`) 作为虚拟控制台。
    * 接收到日志流时自动 `append`，同时为了防止长期运行导致内存溢出 (OOM)，加入了一个简单的限流拦截：当字符数量超过阈值 (`MAX_LOG_SIZE = 10000`) 时，自动截断前半部分。
    * 日志更新时通过 `scrollView.fullScroll(View.FOCUS_DOWN)` 自动滚动到底部，保证始终显示最新的运行状态。


### 5 补充
* **Channel的满缓存策略**
  * BufferOverflow.SUSPEND (默认)：满了就让 send 方法挂起。
  * BufferOverflow.DROP_OLDEST：满了不挂起，直接丢弃最旧的那个数据，把新的放进去。
  * BufferOverflow.DROP_LATEST：满了不挂起，直接丢弃当前准备发送的这个新数据。

* **Contract相关(repeat方法中用到)**
  * Contract 是开发者与编译器之间的法律合同。开发者保证代码运行逻辑，编译器根据合同给予自动类型转换（Smart Cast）和初始化检查放行的福利。
  * 调用位置契约`callsInPlace(lambda, kind)`
    - AT_MOST_ONCE: 最多调用一次（0或1次）。
    - EXACTLY_ONCE: 恰好调用一次。这是最强大的，能让编译器允许你在 Lambda 里初始化 val 变量。
    - AT_LEAST_ONCE: 至少调用一次。
    - UNKNOWN: 不确定（repeat 的默认选项）
  * 条件契约：用于告诉编译器：如果函数返回了特定值，那么某个条件一定成立。 
    - API: returns(value) implies condition