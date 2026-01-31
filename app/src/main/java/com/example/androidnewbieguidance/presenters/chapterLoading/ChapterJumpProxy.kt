package com.example.androidnewbieguidance.presenters.chapterLoading

import android.content.Context
import android.content.Intent
import com.example.foundation.entities.ChapterEntities
import com.example.lesson1_kotlin.LessonKotlinActivity

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/1 14:30
 * @Description:
 */
object ChapterJumpProxy {
  private fun getIntent(context: Context, chapter: ChapterEntities): Intent? {
    return when(chapter){
      is ChapterEntities.Chapter1Kotlin -> Intent(context, LessonKotlinActivity::class.java)
      is ChapterEntities.Chapter2AndroidAndJVM -> null
      else -> null
    }
  }

  fun jumpTo(context: Context, chapter: ChapterEntities){
    val intent = getIntent(context,chapter)
    chapter.bundle?.let{ bundle ->
      intent?.putExtras(bundle)
    }

    context.startActivity(intent)
  }
}