package com.example.chapter3_compose_ui

import android.content.Context
import android.content.Intent
import com.example.chapter3_compose_ui.lesson1.Lesson1BasicUiActivity
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities.Chapter3ComposeGuide

/**
 * @Author: JULIANO
 * @CreateDate: 2026/5/9 23:11
 * @Description:
 */
object ComposeLessonNaviProxy {
  private fun getIntent(context: Context, chapter: Chapter3ComposeGuide): Intent? {
    return when(chapter){
      is Chapter3ComposeGuide.Lesson1BasicUi -> Intent(context, Lesson1BasicUiActivity::class.java)
    }
  }

  fun jumpTo(context: Context, chapter: Chapter3ComposeGuide) : Boolean {
    val intent = getIntent(context, chapter) ?: return false
    context.startActivity(intent)
    return true
  }
}