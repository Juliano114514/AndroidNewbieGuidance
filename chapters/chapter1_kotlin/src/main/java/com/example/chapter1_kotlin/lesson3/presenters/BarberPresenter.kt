package com.example.chapter1_kotlin.lesson3.presenters

import com.example.chapter1_kotlin.lesson3.ConcurrencyType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BarberPresenter : BaseConcurrencyPresenter(ConcurrencyType.BARBER) {
  override suspend fun runAlgorithm(params: Map<String, Int>) {
    val chairsCount = params["chairs"] ?: 3
    val customersCount = params["customers"] ?: 10

    // 使用 Channel 模拟等候区椅子
    val waitingChairs = Channel<Int>(chairsCount)
    val doneChannel = Channel<Unit>()

    printLog("💈 开启理发店：共 $chairsCount 把等候椅，$customersCount 位顾客即将到来")

    val barberJob = demoScope.launch {
      while (true) {
        printLog("😴 理发师在睡觉...")
        val customerId = waitingChairs.receive()
        printLog("✂️ 理发师被顾客 $customerId 唤醒，开始理发！")
        delay((400..800).random().toLong())
        printLog("✅ 理发师给顾客 $customerId 理发完毕。")
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
          doneChannel.receive() // 等待理发完成
          printLog("👋 顾客 $id 满意地离开了。")
        } else {
          printLog("❌ 顾客 $id 发现没有空椅子，愤怒地离开了。")
        }
      }
    }

    customerJobs.forEach { it.join() }
    barberJob.cancel() // 顾客都走完了，理发师下班
  }
}