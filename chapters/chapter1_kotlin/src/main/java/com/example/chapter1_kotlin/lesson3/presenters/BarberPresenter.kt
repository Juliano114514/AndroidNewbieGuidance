package com.example.chapter1_kotlin.lesson3.presenters

import com.example.chapter1_kotlin.lesson3.ConcurrencyType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

/**
 * 顾客到：有空位就坐下，没空位就走，并唤醒理发师
 * 理发师：队列空则睡觉，有人就理发，理完唤醒下一个
 */

class BarberPresenter : BaseConcurrencyPresenter(ConcurrencyType.BARBER) {
  override suspend fun runAlgorithm(params: Map<String, Int>) {
    val chairsCount = params["chairs"] ?: 3
    val customersCount = params["customers"] ?: 10

    // 使用 Channel 模拟等候区椅子
    val waitingChairs = Channel<Int>(chairsCount)
    val doneChannel = Channel<Unit>()

    printLog("🐢载着💈开业牛魔大酬宾🎉！共 $chairsCount 把等候椅，$customersCount 位顾客即将到来")

    val barberJob = demoScope.launch {
      while (true) {
        printLog("😴 理发师在睡觉...")
        // 没有顾客时调用receive会挂起
        val customerId = waitingChairs.receive()
        printLog("✂️ 理发师被顾客 $customerId 唤醒，开始理发！")
        doTask("理发任务")
        printLog("✅ 理发师给顾客 $customerId 理发完毕。")
        // 通知下一个人来理发，唤醒排队队列
        doneChannel.send(Unit)
      }
    }

    val customerJobs = List(customersCount) { id ->
      demoScope.launch {
        delay((100..2000).random().toLong()) // 顾客随机时间到达
        printLog("🚶 顾客 $id 到达理发店。")

        val isSeated = waitingChairs.trySend(id).isSuccess
        if (isSeated) {
          printLog("🪑 顾客 $id 找到空椅子，坐下等待。")
          doneChannel.receive() // 挂起并等待理发完成
          printLog("👋 顾客 $id 满意地离开了。")
        } else {
          printLog("❌ 顾客 $id 发现没有空椅子，愤怒地离开了😡。")
        }
      }
    }

    customerJobs.joinAll()
    barberJob.cancel() // 顾客都走完了，理发师下班
  }
}