package com.example.chapter1_kotlin.lesson3.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.chapter1_kotlin.R
import com.example.chapter1_kotlin.databinding.FragmentConcurrencyBaseBinding
import com.example.chapter1_kotlin.lesson3.ConcurrencyType
import com.example.chapter1_kotlin.lesson3.ParamInjectUtil
import com.example.chapter1_kotlin.lesson3.defaultParams
import com.example.chapter1_kotlin.lesson3.presenters.*
import kotlinx.coroutines.launch

/**
 * @Author: JULIANO
 * @CreateDate: 2026/3/9 15:28
 * @Description:
 */

class ConcurrencyFragment : Fragment(R.layout.fragment_concurrency_base) {
  private var _binding: FragmentConcurrencyBaseBinding? = null
  // 增加安全的 binding 获取，仅在生命周期内使用
  private val binding get() = _binding!!

  private lateinit var type: ConcurrencyType
  private lateinit var presenter: BaseConcurrencyPresenter

  // 存储工具类返回的参数输入框映射
  private var paramInputMap: Map<String, EditText> = emptyMap()

  companion object {
    fun newInstance(type: ConcurrencyType) = ConcurrencyFragment().apply {
      arguments = Bundle().apply {
        putString("TYPE", type.name)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val typeName = arguments?.getString("TYPE") ?: ConcurrencyType.PRODUCER_CONSUMER.name
    type = ConcurrencyType.valueOf(typeName)

    presenter = when (type) {
      ConcurrencyType.PRODUCER_CONSUMER -> ProducerConsumerPresenter()
      ConcurrencyType.READER_WRITER -> ReaderWriterPresenter()
      ConcurrencyType.PHILOSOPHER -> PhilosopherPresenter()
      ConcurrencyType.BARBER -> BarberPresenter()
      ConcurrencyType.SMOKER -> SmokerPresenter()
    }
  }

  // Fragment 使用构造器传入了 LayoutId，这里直接 bind 即可，无需手动 inflate
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    _binding = FragmentConcurrencyBaseBinding.bind(view)

    initViews()
    observeLogs()
  }

  private fun initViews() {
    // 1. 调用 Util 进行组件化注入
    paramInputMap = ParamInjectUtil.injectParamViews(
      requireContext(),
      binding.viewParam.llParamContainer,
      type.defaultParams
    )

    // 2. 绑定开始按钮事件
    binding.viewParam.btnStart.setOnClickListener {
      // 清理上一轮日志
      binding.viewResult.tvConsoleLog.text = ""

      // 快速收集并转换当前所有 EditText 中的值为 Int
      val params = paramInputMap.mapValues { (_, editText) ->
        editText.text.toString().toIntOrNull() ?: 0
      }
      presenter.startDemonstration(params)
    }
  }

  private fun observeLogs() {
    viewLifecycleOwner.lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        presenter.logFlow.collect { log ->
          // 核心优化：使用 append 进行增量绘制，而非全量 text 覆盖
          binding.viewResult.tvConsoleLog.append("$log\n")

          // 简单截断：如果文本过长，清空一半防 OOM (对于演示 Demo 足够高效)
          val currentTextLength = binding.viewResult.tvConsoleLog.text.length
          if (currentTextLength > 10000) {
            val truncated = binding.viewResult.tvConsoleLog.text.substring(currentTextLength - 5000)
            binding.viewResult.tvConsoleLog.text = "...\n$truncated"
          }

          // 保证 ScrollView 自动跟随输出滚到底部
          val scrollView = binding.viewResult.root
          scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
          }
        }
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    presenter.stopDemonstration()
    _binding = null // 避免内存泄漏
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }
}