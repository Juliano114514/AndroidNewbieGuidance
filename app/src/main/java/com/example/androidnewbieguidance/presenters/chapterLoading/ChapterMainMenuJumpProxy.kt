package com.example.androidnewbieguidance.presenters.chapterLoading

import android.content.Context
import android.content.Intent
import com.example.kn_shared.utils.chapterJump.ChapterJumpEntities.AppLevel

import com.example.lesson1_kotlin.LessonKotlinActivity

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/1 14:30
 * @Description:
 */
object ChapterMainMenuJumpProxy {
  private fun getIntent(context: Context, chapter: AppLevel): Intent? {
    return when(chapter){
      is AppLevel.ToChapter1Kotlin -> Intent(context, LessonKotlinActivity::class.java)
      is AppLevel.ToChapter2Android -> null
    }
  }

  fun jumpTo(context: Context, chapter: AppLevel) : Boolean {
    val intent = getIntent(context,chapter)
    if(intent == null) return false
    context.startActivity(intent)
    return true
  }
}