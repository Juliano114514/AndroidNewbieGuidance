package com.example.chapter1_kotlin.lesson3.presenters

import com.example.chapter1_kotlin.lesson3.ConcurrencyType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SmokerPresenter : BaseConcurrencyPresenter(ConcurrencyType.SMOKER) {
  override suspend fun runAlgorithm(params: Map<String, Int>) {
    val rounds = params["rounds"] ?: 5

    // 0: 烟草, 1: 纸, 2: 火柴
    val table = Channel<Int>()
    val agentDone = Channel<Unit>()
    val ingredients = listOf("烟草", "纸", "火柴")

    printLog("🚬 开启吸烟者模型：演示 $rounds 轮")

    val smokers = List(3) { id ->
      demoScope.launch {
        while (true) {
          val missingItem = table.receive()
          if (missingItem == id) { // 提供的正好是自己缺少的另外两样（此处的逻辑抽象为：桌上缺少的等于自己拥有的）
            printLog("😃 吸烟者 $id (自带 ${ingredients[id]}) 拿到了桌上的另外两样材料，开始卷烟...")
            delay((300..500).random().toLong())
            printLog("💨 吸烟者 $id 正在抽烟...")
            delay((400..600).random().toLong())
            printLog("✅ 吸烟者 $id 抽完了，通知供应者。")
            agentDone.send(Unit)
          } else {
            // 不是自己要的材料，放回桌上
            table.send(missingItem)
            delay(50) // 稍作延时防止死循环占用
          }
        }
      }
    }

    for (i in 1..rounds) {
      val supplied = (0..2).random()
      printLog("👨‍💼 供应者在桌上放下了 【${ingredients[(supplied + 1) % 3]}】 和 【${ingredients[(supplied + 2) % 3]}】 (需要 ${ingredients[supplied]} 的人可以抽烟)")
      table.send(supplied)
      agentDone.receive() // 等待当前吸烟者抽完
      printLog("🔄 第 $i 轮结束。\n")
    }

    smokers.forEach { it.cancel() }
  }
}