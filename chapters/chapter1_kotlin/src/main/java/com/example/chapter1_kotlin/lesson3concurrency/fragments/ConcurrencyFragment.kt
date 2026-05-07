package com.example.chapter1_kotlin.lesson3concurrency.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.chapter1_kotlin.R
import com.example.chapter1_kotlin.databinding.FragmentConcurrencyBaseBinding
import com.example.chapter1_kotlin.lesson3concurrency.ConcurrencyType
import com.example.chapter1_kotlin.lesson3concurrency.ParamInjectUtil
import com.example.chapter1_kotlin.lesson3concurrency.defaultParams
import com.example.chapter1_kotlin.lesson3concurrency.presenters.*
import kotlinx.coroutines.launch

/**
 * @Author: JULIANO
 * @CreateDate: 2026/3/9 15:28
 * @Description: 基础页面设计
 */

class ConcurrencyFragment : Fragment(R.layout.fragment_concurrency_base) {
  private var _binding: FragmentConcurrencyBaseBinding? = null

  private lateinit var type: ConcurrencyType
  private lateinit var presenter: BaseConcurrencyPresenter

  // 存储工具类返回的参数输入框映射
  private var paramInputMap: Map<String, EditText> = emptyMap()

  companion object {
    const val TYPE = "type"
    const val MAX_LOG_SIZE = 10000
    fun newInstance(type: ConcurrencyType) = ConcurrencyFragment().apply {
      arguments = Bundle().apply {
        putString(TYPE, type.name)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val typeName = arguments?.getString(TYPE) ?: ConcurrencyType.PRODUCER_CONSUMER.name
    type = ConcurrencyType.valueOf(typeName) // 根据枚举类的typename解析对应的type类型

    presenter = when (type) {
      ConcurrencyType.PRODUCER_CONSUMER -> ProducerConsumerPresenter()
      ConcurrencyType.READER_WRITER -> ReaderWriterPresenter()
      ConcurrencyType.PHILOSOPHER -> PhilosopherPresenter()
      ConcurrencyType.BARBER -> BarberPresenter()
      ConcurrencyType.SMOKER -> SmokerPresenter()
      ConcurrencyType.SWITCH_PROCESS -> SwitchProcessPresenter()
    }
  }

  // 在 onCreateView 中返回了 return inflater.inflate(mContentLayoutId, container, false)
  // 因而这里的 view 实际上就是 binding.root, 不需要再 inflate 创建，直接 bind 绑定即可
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    _binding = FragmentConcurrencyBaseBinding.bind(view)

    initViews()
    observeLogs()
  }

  private fun initViews() {
    val binding = _binding ?: return

    // 调用 Util 进行组件化注入
    paramInputMap = ParamInjectUtil.injectParamViews(
      requireContext(),
      binding.viewParam.llParamContainer,
      type.defaultParams
    )

    // 绑定开始按钮事件
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

  @SuppressLint("SetTextI18n")
  private fun observeLogs() {
    viewLifecycleOwner.lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        presenter.logFlow.collect { log ->
          updateLogUi(log)
        }
      }
    }
  }

  @SuppressLint("SetTextI18n")
  private fun updateLogUi(log: String){
    val binding = _binding ?: return
    binding.viewResult.tvConsoleLog.apply {
      append("$log\n")

      if(this.text.length > MAX_LOG_SIZE){
        text = "...\n${text.substring(text.length / 2)}"
      }

      val scrollView = binding.viewResult.root
      scrollView.post {
        scrollView.fullScroll(View.FOCUS_DOWN)  // TODO: 新开一课学一下焦点控制orz
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