package com.example.chapter3_compose_ui

import android.content.Context
import android.content.Intent
import com.example.chapter3_compose_ui.lesson1basic_ui.Lesson1BasicUiActivity
import com.example.chapter3_compose_ui.lesson2modifier.Lesson2ModifierActivity
import com.example.chapter3_compose_ui.lesson3advanced_and_preview.Lesson3PreviewActivity
import com.example.chapter3_compose_ui.ui.custom.CustomSettingActivity
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
      is Chapter3ComposeGuide.Lesson2Modifier -> Intent(context, Lesson2ModifierActivity::class.java)
      is Chapter3ComposeGuide.Lesson3AdvancedAndPreView -> Intent(context, Lesson3PreviewActivity::class.java)
      is Chapter3ComposeGuide.CustomizeTheme -> Intent(context, CustomSettingActivity::class.java)
    }
  }

  fun jumpTo(context: Context, chapter: Chapter3ComposeGuide) : Boolean {
    val intent = getIntent(context, chapter) ?: return false
    context.startActivity(intent)
    return true
  }
}