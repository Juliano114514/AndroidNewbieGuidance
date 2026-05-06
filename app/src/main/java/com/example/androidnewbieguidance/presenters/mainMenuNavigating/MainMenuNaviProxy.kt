package com.example.androidnewbieguidance.presenters.mainMenuNavigating

import android.content.Context
import android.content.Intent
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities.MainMenu

import com.example.chapter1_kotlin.LessonKotlinActivity

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/1 14:30
 * @Description:
 */
object MainMenuNaviProxy {
  private fun getIntent(context: Context, chapter: MainMenu): Intent? {
    return when(chapter){
      is MainMenu.ToChapter1Kotlin -> Intent(context, LessonKotlinActivity::class.java)
      is MainMenu.ToChapter2Android -> null
      is MainMenu.ToChapter3Compose -> null
    }
  }

  fun jumpTo(context: Context, chapter: MainMenu) : Boolean {
      val intent = getIntent(context, chapter) ?: return false
      context.startActivity(intent)
    return true
  }
}