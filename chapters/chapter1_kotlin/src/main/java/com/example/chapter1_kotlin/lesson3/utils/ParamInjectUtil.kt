package com.example.chapter1_kotlin.lesson3

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.chapter1_kotlin.R

object ParamInjectUtil {

  /**
   * 根据配置列表，向指定的容器动态注入输入框
   * @return 返回参数 Key 到 EditText 实例的映射表，方便后续一键取值
   */
  fun injectParamViews(
    context: Context,
    container: ViewGroup,
    configs: List<ParamConfig>
  ): Map<String, EditText> {
    // 每次注入前清空旧视图，防止复用时叠加
    container.removeAllViews()
    val paramInputMap = mutableMapOf<String, EditText>()
    val inflater = LayoutInflater.from(context)

    configs.forEach { config ->
      // 将 item_param_input 挂载到 container
      val itemView = inflater.inflate(R.layout.item_param_input, container, false)
      val tvName = itemView.findViewById<TextView>(R.id.tv_param_name)
      val etValue = itemView.findViewById<EditText>(R.id.et_param_value)

      tvName.text = config.name
      etValue.setText(config.defaultValue.toString())

      container.addView(itemView)
      paramInputMap[config.key] = etValue
    }
    return paramInputMap
  }
}