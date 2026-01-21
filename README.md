# 目录 (完成的会贴上超链接)

## 1. 基础编程语言与语法

### 1.1 Kotlin 核心语法

- 1.1.1 特有关键字（by/inline/object/companion object 等）
- 1.1.2 函数式编程（高阶函数 / 扩展函数 / 委托函数）
- 1.1.3 空安全与类型系统
- 1.1.4 集合与流（Collection/Flow/Sequence）

### 1.2 Kotlin 与 JVM 交互

- 1.2.1 Kotlin 调用 Java 代码
- 1.2.2 Java 调用 Kotlin 代码（@JvmStatic/@JvmField 等）
- 1.2.3 Kotlin 字节码编译机制

### 1.3 Kotlin 协程

- 1.3.1 协程基础（CoroutineScope/Job/Deferred）
- 1.3.2 协程调度器（Dispatchers）
- 1.3.3 协程异常处理
- 1.3.4 大批量网络 / IO 任务的协程优化方案

## 2. 安卓基础核心知识

### 2.1 JVM 与安卓运行时

- 2.1.1 JVM 架构（类加载 / 内存模型 / 垃圾回收）
- 2.1.2 ART/Dalvik 虚拟机（与 JVM 差异）
- 2.1.3 安卓类加载器与资源加载

### 2.2 线程与并发

- 2.2.1 线程基础（创建 / 启动 / 状态）
- 2.2.2 线程安全（同步锁 /volatile/ 原子类）
- 2.2.3 线程池（ThreadPoolExecutor/AsyncTask/HandlerThread）
- 2.2.4 安卓消息机制（Handler/Looper/MessageQueue）

### 2.3 视图系统

- 2.3.1 View/ViewGroup 核心原理
    - 2.3.1.1 View 绘制流程（measure/layout/draw）
    - 2.3.1.2 ViewGroup 布局策略

- 2.3.2 事件分发机制
    - 2.3.2.1 点击事件分发（TouchEvent/onInterceptTouchEvent）
    - 2.3.2.2 滑动冲突处理

- 2.3.3 特殊渲染视图
    - 2.3.3.1 TextureView（原理 / 生命周期 / 渲染适配）
    - 2.3.3.2 SurfaceView（原理 / 双缓冲 / 性能优化）
    - 2.3.3.3 TextureView/SurfaceView 差异与场景选型

- 2.3.4 视图生命周期（Activity/Fragment/View 联动）
- 2.3.5 分页与滑动组件
    - 2.3.5.1 ViewPager2（原理 / 适配 / 滑动优化）
    - 2.3.5.2 Paging3（分页加载 / 数据缓存 / 状态管理）

### 2.4 自定义 View

- 2.4.1 自定义 View 分类（继承 View/ViewGroup）
- 2.4.2 自定义属性（attr.xml/TypeArray）
- 2.4.3 自定义 View 测量与绘制
- 2.4.4 自定义 View 事件处理

### 2.5 安卓任务与进程

- 2.5.1 任务栈（TaskStack）与返回栈
- 2.5.2 Activity 启动模式（standard/singleTop/singleTask/singleInstance）
- 2.5.3 进程分类（前台 / 后台 / 服务进程）
- 2.5.4 后台杀进程机制与保活策略

### 2.6 原生库与资源加载

- 2.6.1 SO 文件加载机制
- 2.6.2 SO 文件依赖关系分析
- 2.6.3 CDN 下发 SO / 资源的流程与优化

## 3. 网络与数据传输

### 3.1 HTTP 基础

- 3.1.1 HTTP 核心（请求方法 / 状态码 / 头部字段）
- 3.1.2 HTTP 版本差异（HTTP1.1/HTTP2/HTTP3）
- 3.1.3 HTTPS 原理（SSL/TLS/ 证书验证）

### 3.2 网络工具与库

- 3.2.1 Curl 指令（常用参数 / 调试场景）
- 3.2.2 OkHttp（拦截器 / 连接池 / 超时配置）
- 3.2.3 Retrofit（注解 / 转换器 / 协程适配器）
- 3.2.4 其他网络库（Volley/AsyncHttpClient）

### 3.3 缓存机制

- 3.3.1 HTTP 缓存（强缓存 / 协商缓存）
- 3.3.2 客户端缓存（内存缓存 / 磁盘缓存）
- 3.3.3 网络库缓存配置（OkHttp 缓存策略）

### 3.4 数据序列化

- 3.4.1 JSON 解析（Gson/Moshi/Jackson）
- 3.4.2 Protobuf（定义 / 编译 / 跨端适配）

## 4. 安卓音视频核心技术

### 4.1 音视频基础理论

- 4.1.1 音频基础（采样率 / 位深 / 声道 / 编码格式）
- 4.1.2 视频基础（帧率 / 分辨率 / 码率 / 编码格式）
- 4.1.3 音视频同步原理

### 4.2 音视频渲染

- 4.2.1 Surface/SurfaceHolder 核心原理
- 4.2.2 TextureView 视频渲染（预览适配 / 帧刷新）
- 4.2.3 SurfaceView 视频渲染（双缓冲 / 性能优化）
- 4.2.4 自定义预览视图（IPreviewView 接口设计）

### 4.3 音视频编解码

- 4.3.1 安卓原生编解码（MediaCodec）
- 4.3.2 FFmpeg（编译 / 集成 / 常用 API）
- 4.3.3 常用音视频解码 SDK（封装层 / 接入适配）
- 4.3.4 硬解码 / 软解码差异与选型

### 4.4 播放器开发

- 4.4.1 自定义播放器架构（VideoPlayer 封装）
- 4.4.2 播放器生命周期管理（与 Activity/Fragment 联动）
- 4.4.3 帧渲染回调与刷新（onFrameRender/requestRenderUpdate）
- 4.4.4 播放器状态管理（播放 / 暂停 / 停止 / 进度调整）

### 4.5 视频预览技术

- 4.5.1 视频预览页面架构（Activity/Fragment 封装）
- 4.5.2 预览播放器绑定（PreviewPlayer 与 TextureView）
- 4.5.3 编辑桥接（EditorBridge）与播放器更新（PlayerProjectUpdater）
- 4.5.4 预览帧更新与视图刷新（invalidate 机制）

## 5. 数据存储与持久化

### 5.1 键值对存储

- 5.1.1 SharedPreferences（原理 / 线程安全 / 性能问题）
- 5.1.2 MMKV（原理 / 性能优化 / 跨进程）

### 5.2 文件存储

- 5.2.1 内部存储 / 外部存储
- 5.2.2 文件操作（IO 流 / FileProvider）

### 5.3 数据库存储

- 5.3.1 Room（ORM / 协程支持 / 数据迁移）
- 5.3.2 SQLite（原生操作 / 性能优化）

### 5.4 缓存存储

- 5.4.1 内存缓存（LruCache）
- 5.4.2 磁盘缓存（DiskLruCache）

## 6. 架构设计与工程化

### 6.1 架构模式

- 6.1.1 MVVM（ViewModel/LiveData/StateFlow）
- 6.1.2 MVI（单向数据流 / 状态建模）
- 6.1.3 MVP（契约模式 / 解耦）

### 6.2 依赖注入

- 6.2.1 Koin（轻量级 DI / 模块配置）
- 6.2.2 Dagger/Hilt（编译期 DI / 安卓适配）

### 6.3 工程优化

- 6.3.1 包体积优化

    - 6.3.1.1 混淆与压缩（R8/D8/ProGuard）
    - 6.3.1.2 混淆规则（@Keep / 自定义规则）
    - 6.3.1.3 资源 / 代码裁剪



- 6.3.2 性能优化（启动 / 内存 / 卡顿）

- 6.3.3 编译优化（Gradle 配置 / 增量编译）

### 6.4 调试与测试

- 6.4.1 音视频调试（日志 / 抓包 / 帧分析）
- 6.4.2 单元测试 / UI 测试（JUnit/Espresso）
- 6.4.3 性能分析工具（Profiler/Mat）

## 7. 跨端与多端开发

### 7.1 KMP（Kotlin Multiplatform）

- 7.1.1 KMP 基础（共享代码 / 平台适配）
- 7.1.2 安卓与 iOS 代码交互
- 7.1.3 KMP 音视频模块共享

### 7.2 跨语言交互

- 7.2.1 JNI/NDK（Java 与 C/C++ 交互）
- 7.2.2 跨进程通信（AIDL/Messenger/ContentProvider）

### 7.3 多端数据协议

- 7.3.1 Protobuf 跨端适配
- 7.3.2 跨端数据解析与兼容

## 8. 音视频进阶与实战

### 8.1 音视频编辑

- 8.1.1 视频剪辑 / 拼接 / 转码
- 8.1.2 音频混流 / 音效处理

### 8.2 播放器进阶

- 8.2.1 倍速播放 / 高清切换
- 8.2.2 播放器预加载 / 缓存策略
- 8.2.3 播放器异常处理（断网 / 格式不支持）

### 8.3 音视频实战场景

- 8.3.1 视频预览页面实战（TextureView+VideoPlayer）
- 8.3.2 自定义音视频编辑页面开发
- 8.3.3 音视频导出与分享