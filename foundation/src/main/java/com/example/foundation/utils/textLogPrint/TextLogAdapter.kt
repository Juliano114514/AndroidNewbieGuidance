package com.example.foundation.utils.textLogPrint

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author: JULIANO
 * @CreateDate: 2026/3/14 19:18
 * @Description:
 */
// ConsoleLogAdapter.kt (可放在 Foundation 中)
class ConsoleLogAdapter : ListAdapter<TextLogEntry, ConsoleLogAdapter.LogViewHolder>(DiffCallback) {

  // 维护一个解耦的颜色组（可以使用你 foundation/colors.xml 中的颜色）
  private val colorPool = listOf(
    "#FF6200EE".toColorInt(), // purple_500
    "#FF018786".toColorInt(), // teal_700
    "#1976D2".toColorInt(),   // Blue
    "#E64A19".toColorInt(),   // Orange
    "#388E3C".toColorInt()    // Green
  )

  // 基于角色名称的 Hash 分配固定颜色，极其优雅的解耦方式
  private fun getColorForRole(role: String): Int {
    if (role == "System") return "#9E9E9E".toColorInt() // 系统灰色
    val hash = kotlin.math.abs(role.hashCode())
    return colorPool[hash % colorPool.size]
  }

  class LogViewHolder(val tv: TextView) : RecyclerView.ViewHolder(tv)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
    val tv = TextView(parent.context).apply {
      textSize = 13f
      setPadding(0, 6, 0, 6)
      setTextColor("#333333".toColorInt()) // 默认字体颜色
    }
    return LogViewHolder(tv)
  }

  override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
    val item = getItem(position)

    // 核心：使用 SpannableStringBuilder 构建富文本
    val builder = SpannableStringBuilder()
    builder.append("${item.emoji} ")

    // 渲染 [角色]
    val roleStart = builder.length
    builder.append("[${item.role}]")
    val roleColor = getColorForRole(item.role)
    builder.setSpan(ForegroundColorSpan(roleColor), roleStart, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    builder.setSpan(StyleSpan(Typeface.BOLD), roleStart, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    // 渲染 [动作] 和 描述
    if (item.action.isNotEmpty()) {
      builder.append(" [${item.action}] ")
    } else {
      builder.append(" ")
    }
    builder.append(item.description)

    holder.tv.text = builder
  }

  object DiffCallback : DiffUtil.ItemCallback<TextLogEntry>() {
    override fun areItemsTheSame(oldItem: TextLogEntry, newItem: TextLogEntry) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: TextLogEntry, newItem: TextLogEntry) = oldItem == newItem
  }
}