package com.example.chapter2_core_and_view.lesson2room.utils

import com.example.foundation.utils.toast.ToastUtil

/**
 * Lesson2Room 场景的学生相关 Toast 入口，集中文案便于维护与扩展。
 */
object StudentToastHelper {

  fun showUpdateScoreSuccess(studentName: String, isDeduction: Boolean) {
    val text =
      if (isDeduction) "同学 $studentName 报下学号，扣A分喵😡"
      else "同学 $studentName 报下学号，加A分喵😋"
    ToastUtil.showSuccess(text)
  }

  /** [keyBlank] 为 true 表示未输入检索关键词（随机挑人但列表为空等场景沿用原语义）。 */
  fun showLookupFailed(keyBlank: Boolean) {
    ToastUtil.showWarn(if (keyBlank) "没有知识的荒原喵" else "查无此人喵")
  }

  fun showDeleteSuccess(studentName: String) {
    ToastUtil.showSuccess("已将 $studentName 移出群聊")
  }

  fun showRandomStudentEnrolled(studentName: String) {
    ToastUtil.showSuccess("随机新生 $studentName 已入学喵")
  }

  fun showInsertSuccess(studentName: String) {
    ToastUtil.showSuccess("插班生 $studentName 已加入速达优喵")
  }

  fun showInsertNameRequired() {
    ToastUtil.showWarn("请先输入姓名喵")
  }

  fun showClearAllSuccess() {
    ToastUtil.showSuccess("全班已清空，知识归零喵")
  }
}
