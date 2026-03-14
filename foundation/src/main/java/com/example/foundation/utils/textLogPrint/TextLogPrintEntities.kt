package com.example.foundation.utils.textLogPrint

/**
 * @Author: JULIANO
 * @CreateDate: 2026/3/14 19:01
 * @Description:
 */

data class TextLogEntry(
  val id: Long = System.currentTimeMillis(), // 唯一ID，用于 DiffUtil
  val emoji: String = "",
  val role: String,           // 角色：如 "生产者 0"
  val action: String,         // 操作类型：如 "生产"
  val description: String,    // 详情描述
  val isSystem: Boolean = false // 是否是系统级提示
)
