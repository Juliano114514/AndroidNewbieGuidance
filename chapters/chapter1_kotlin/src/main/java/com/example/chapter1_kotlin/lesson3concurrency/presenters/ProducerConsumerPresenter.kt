package com.example.chapter1_kotlin.lesson3concurrency.presenters

import com.example.chapter1_kotlin.lesson3concurrency.ConcurrencyType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * @Author: JULIANO
 * @CreateDate: 2026/3/9 17:08
 * @Description:
 */
class ProducerConsumerPresenter : BaseConcurrencyPresenter(ConcurrencyType.PRODUCER_CONSUMER) {
  override suspend fun runAlgorithm(params: Map<String, Int>) {
    val producerCount = params["producers"] ?: 3
    val consumerCount = params["consumers"] ?: 2
    val target = params["target"] ?: 100

    // 仓库容量设为 10
    val channel = Channel<Int>(10)
    var currentProduced = 0
    var currentConsumed = 0
    val countMutex = Mutex()

    printLog("初始化完成：$producerCount 个生产者，$consumerCount 个消费者，目标 $target 个")

    // 启动生产者协程
    val producers = List(producerCount) { id ->
      demoScope.launch {
        while (true) {
          val item = countMutex.withLock {
            if (currentProduced >= target) return@launch
            currentProduced++
          }
          delay((100..300).random().toLong()) // 模拟生产耗时

          // 当Channel缓冲区满了，生产者协程会被挂起，直至有人receive后才会唤起
          // 如果使用的是 trySend，碰到队满直接返回 ChannelResult.failed()
          channel.send(item)
          printLog(
            "👨‍🍳 生产者 $id 生产了商品 #$item)"
          )
        }
      }
    }

    // 启动消费者协程
    val consumers = List(consumerCount) { id ->
      demoScope.launch {
        // 自动迭代：直到 channel.close() 且数据取完
        for (item in channel) {
          delay((200..400).random().toLong())
          printLog("😋 消费者 $id 消费了 #$item")
        }
        printLog("🏁 消费者 $id 已退出")
      }
    }

    // 等待所有生产者和消费者完成
    producers.joinAll()
    channel.close() // 通知消费者不再有新内容生产了
    consumers.joinAll()
  }
}