package com.example.chapter2_core_and_view.lessonnavi

import android.content.Context
import android.content.Intent
import com.example.chapter2_core_and_view.lesson1ksp.Lesson1KspActivity
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities.Chapter2AndroidGuide

object CoreAndViewNaviProxy {
  private fun getIntent(context: Context, chapter: Chapter2AndroidGuide): Intent? {
    return when(chapter){
      is Chapter2AndroidGuide.Lesson1Ksp -> Intent(context, Lesson1KspActivity::class.java)
    }
  }

  fun jumpTo(context: Context, chapter: Chapter2AndroidGuide) : Boolean {
    val intent = getIntent(context, chapter) ?: return false
    context.startActivity(intent)
    return true
  }
}