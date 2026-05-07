package com.example.chapter1_kotlin.lesson3concurrency.presenters

import com.example.chapter1_kotlin.lesson3concurrency.ConcurrencyType
import com.example.foundation.base.presenter.BasePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * @Author: JULIANO
 * @CreateDate: 2026/3/9 15:39
 * @Description: 并发Tab 基础 presenter
 */
abstract class BaseConcurrencyPresenter(val type: ConcurrencyType) : BasePresenter() {
  private val _logFlow = MutableSharedFlow<String>(extraBufferCapacity = 100)
  val logFlow = _logFlow.asSharedFlow()

  private var job: Job? = null
  protected val demoScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

  // 接收 Fragment 传递过来的参数并启动
  fun startDemonstration(params: Map<String, Int>) {
    job?.cancel() // 1. 取消上一次的演示
    job = demoScope.launch {
      _logFlow.emit("\n=== 开始演示：${type.title} ===")
      runAlgorithm(params)
      _logFlow.emit("=== 演示结束 ===")
    }
  }

  protected suspend fun printLog(msg: String) {
    _logFlow.emit(msg)
  }

  fun stopDemonstration() {
    job?.cancel()
  }

  abstract suspend fun runAlgorithm(params: Map<String, Int>)

  protected open suspend fun doTask(taskDescription: String = "Mocked Task") {
    val time = (800..2000).random().toLong()
    printLog("⏳ $taskDescription，预计耗时 ${time}ms...")
    delay(time)
  }

  override fun onDestroy() {
    super.onDestroy()
    demoScope.cancel()
  }
}