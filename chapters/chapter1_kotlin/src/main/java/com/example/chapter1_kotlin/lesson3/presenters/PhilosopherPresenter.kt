package com.example.chapter1_kotlin.lesson3.presenters

import com.example.chapter1_kotlin.lesson3.ConcurrencyType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * @Author: JULIANO
 * @CreateDate: 2026/3/9 17:10
 * @Description:
 */
class PhilosopherPresenter : BaseConcurrencyPresenter(ConcurrencyType.PHILOSOPHER) {
  override suspend fun runAlgorithm(params: Map<String, Int>) {
    val count = params["count"] ?: 5
    val rounds = 3 // 为了演示不至于无限进行，设置每个人吃 3 轮就结束

    val forks = Array(count) { Mutex() }

    printLog("开启哲学家晚宴：共 $count 位哲学家")

    val philosophers = List(count) { id ->
      demoScope.launch {
        for (r in 1..rounds) {
          printLog("🤔 哲学家 $id 正在思考 (第$r 轮)...")
          delay((300..600).random().toLong())

          val leftFork = forks[id]
          val rightFork = forks[(id + 1) % count]

          // 核心防死锁：奇数哲学家先拿左边，偶数先拿右边；或者最后一个哲学家顺序相反
          val firstFork = if (id == count - 1) rightFork else leftFork
          val secondFork = if (id == count - 1) leftFork else rightFork
          val firstForkName = if (id == count - 1) "右筷子" else "左筷子"
          val secondForkName = if (id == count - 1) "左筷子" else "右筷子"

          firstFork.withLock {
            printLog("🖐 哲学家 $id 拿起了 $firstForkName")
            secondFork.withLock {
              printLog("🍝 哲学家 $id 拿起了 $secondForkName，开始进餐！")
              delay((400..800).random().toLong()) // 模拟进餐
              printLog("放下 哲学家 $id 放下了两根筷子")
            }
          }
        }
        printLog("✅ 哲学家 $id 用餐完毕，离席。")
      }
    }

    philosophers.forEach { it.join() }
  }
}