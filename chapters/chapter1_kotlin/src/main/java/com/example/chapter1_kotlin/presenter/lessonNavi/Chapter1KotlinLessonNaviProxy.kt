package com.example.chapter1_kotlin.presenter.lessonNavi

import android.content.Context
import android.content.Intent
import com.example.chapter1_kotlin.lesson2.ExtendActivity
import com.example.chapter1_kotlin.lesson3.Lesson3ConcurrencyActivity
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities.Chapter1KotlinGuide

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/26 11:01
 * @Description:
 */
object Chapter1KotlinLessonNaviProxy {
  private fun getIntent(context: Context, chapter: Chapter1KotlinGuide): Intent? {
    return when(chapter){
      is Chapter1KotlinGuide.Lesson2ExtendFunction -> Intent(context, ExtendActivity::class.java)
      is Chapter1KotlinGuide.Lesson3Coroutine -> Intent(context, Lesson3ConcurrencyActivity::class.java)
    }
  }

  fun jumpTo(context: Context, chapter: Chapter1KotlinGuide) : Boolean {
    val intent = getIntent(context,chapter)
    if(intent == null) return false
    context.startActivity(intent)
    return true
  }
}