package com.example.chapter1_kotlin.lesson3concurrency.presenters

import com.example.chapter1_kotlin.lesson3concurrency.ConcurrencyType
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class ReaderWriterPresenter : BaseConcurrencyPresenter(ConcurrencyType.READER_WRITER) {
  override suspend fun runAlgorithm(params: Map<String, Int>) {
    val readersCount = params["readers"] ?: 5
    val writersCount = params["writers"] ?: 2

    val readMutex = Mutex()
    val writeMutex = Mutex()
    var activeReaders = 0
    var sharedData = 0

    printLog("📚 开启读者写者模型：共 $readersCount 个读者，$writersCount 个写者")

    val writerJobs = List(writersCount) { id ->
      demoScope.launch {
        repeat(3) { // 每个写者写3次
          delay((500..1000).random().toLong())
          printLog("✍️ 写者 $id 写之前一定要拿写锁...")
          // 拿到写锁之后，所有人都不能再进入临界区 \😭/
          writeMutex.withLock {
            printLog("✍️ 写者 $id 一定要会写东西...")
            delay((300..600).random().toLong())
            sharedData = Random.nextInt(1,4)
            printLog("✅ 写者 $id 写入完毕，当前数据: $sharedData")
          }
        }
      }
    }

    val readerJobs = List(readersCount) { id ->
      demoScope.launch {
        repeat(3) { // 每个读者读3次
          delay((300..800).random().toLong())

          // 读锁用于维护读者计数，避免并发问题
          // 且当写者在写时，读者会卡在申请写锁的地方，并且不释放读锁
          readMutex.withLock {
            activeReaders++
            if (activeReaders == 1) {
              writeMutex.lock() // 第一个读者负责获取写锁，阻止写者
            }
          }

          // 读者读期间是不上锁的，仅持有了写者锁
          printLog("📖 读者 $id 正在：你从 ${sharedData} 冬来，换我 ${sharedData} 城雪白\\😭/ (当前有 $activeReaders 个读者)")
          delay((200..400).random().toLong())

          readMutex.withLock {
            activeReaders--
            if (activeReaders == 0) {
              writeMutex.unlock() // 最后一个读者释放写锁
            }
          }
        }
      }
    }

    writerJobs.joinAll()
    readerJobs.joinAll()
  }
}