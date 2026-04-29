package com.example.chapter1_kotlin.lesson3.presenters

import com.example.chapter1_kotlin.lesson3.ConcurrencyType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SwitchProcessPresenter : BaseConcurrencyPresenter(ConcurrencyType.SWITCH_PROCESS) {
    override suspend fun runAlgorithm(params: Map<String, Int>) {
        val target = params["target"] ?: 20
        var cnt = 0

        // 用channel实现唤醒/交替
        val turnA = Channel<Unit>(1)
        val turnB = Channel<Unit>(1)

        val jobA = demoScope.launch{
            while(true){
                // 因为channel缓存为1，再次抵达此处时，会因为饥饿而导致协程挂起
                // 只有当B中send信号A之后，这里才能继续执行，进而实现了交替执行的逻辑
                turnA.receive() // 阻塞等到A发信号
                if(cnt >= target){
                    turnB.trySend(Unit) // 退出前唤醒可能正在等的B
                    break;
                }
                cnt++
                printLog("🅰️ 协程A 执行，当前cnt: $cnt")
                delay(100)
                turnB.send(Unit)
            }
        }

        val jobB = demoScope.launch {
            while (true) {
                turnB.receive() // 阻塞等待轮到 B 执行的信号
                if (cnt >= target) {
                    turnA.trySend(Unit) // 退出前唤醒可能在等待的 A
                    break
                }
                cnt++
                printLog("🅱️ 协程B 执行，当前cnt: $cnt")
                delay(100) // 模拟要求中的耗时操作
                turnA.send(Unit) // 通知并唤醒 A 执行
            }
        }

        printLog("====== 开始交替执行 ======")

        // 发送初始信号，点火启动协程 A
        turnA.send(Unit)

        // 等待演示结束
        jobA.join()
        jobB.join()

        turnA.close()
        turnB.close()
    }
}