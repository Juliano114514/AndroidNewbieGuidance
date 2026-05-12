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
    object ToChapter3Compose : MainMenu()
  }
  sealed class Chapter1KotlinGuide : ChapterNaviEntities() {
    object Lesson2ExtendFunction : Chapter1KotlinGuide()
    object Lesson3Coroutine : Chapter1KotlinGuide()
    object Lesson4Proxy : Chapter1KotlinGuide()
  }

  sealed class Chapter2AndroidGuide : ChapterNaviEntities() {
    object Lesson1Ksp : Chapter2AndroidGuide()
  }

  sealed class Chapter3ComposeGuide : ChapterNaviEntities() {
    object Lesson1BasicUi : Chapter3ComposeGuide()
    object Lesson2Modifier : Chapter3ComposeGuide()
  }

}
