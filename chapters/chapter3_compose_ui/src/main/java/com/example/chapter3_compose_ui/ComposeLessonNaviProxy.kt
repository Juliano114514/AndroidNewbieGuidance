package com.example.chapter3_compose_ui

import android.content.Context
import android.content.Intent
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities.Chapter3ComposeGuide

/**
 * @Author: JULIANO
 * @CreateDate: 2026/5/9 23:11
 * @Description:
 */
object ComposeLessonNaviProxy {
  private fun getIntent(context: Context, chapter: Chapter3ComposeGuide): Intent? {
    return null
  }

  fun jumpTo(context: Context, chapter: Chapter3ComposeGuide) : Boolean {
    val intent = getIntent(context, chapter) ?: return false
    context.startActivity(intent)
    return true
  }
}