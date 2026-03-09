package com.example.chapter1_kotlin.lesson3.presenters

import com.example.chapter1_kotlin.lesson3.ConcurrencyType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

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
          printLog("✍️ 写者 $id 尝试获取写锁...")
          writeMutex.withLock {
            printLog("✍️ 写者 $id 正在写入数据...")
            delay((300..600).random().toLong())
            sharedData++
            printLog("✅ 写者 $id 写入完毕，当前数据: $sharedData")
          }
        }
      }
    }

    val readerJobs = List(readersCount) { id ->
      demoScope.launch {
        repeat(3) { // 每个读者读3次
          delay((300..800).random().toLong())

          readMutex.withLock {
            activeReaders++
            if (activeReaders == 1) {
              writeMutex.lock() // 第一个读者负责获取写锁，阻止写者
            }
          }

          printLog("📖 读者 $id 正在读取数据: $sharedData (当前共有 $activeReaders 个读者)")
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

    writerJobs.forEach { it.join() }
    readerJobs.forEach { it.join() }
  }
}