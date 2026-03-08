package com.example.kn_shared.utils.chapterNavi

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/21 18:18
 * @Description:
 */
sealed class ChapterNaviEntities{

  sealed class MainMenu : ChapterNaviEntities() {
    object ToChapter1Kotlin : MainMenu()
    object ToChapter2Android : MainMenu()
  }
  sealed class Chapter1KotlinGuide : ChapterNaviEntities() {
    object Lesson2ExtendFunction : Chapter1KotlinGuide()
    object Lesson3Coroutine : Chapter1KotlinGuide()
  }

}
