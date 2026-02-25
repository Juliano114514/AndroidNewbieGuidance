package com.example.kn_shared.utils.chapterJump

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/21 18:18
 * @Description:
 */
sealed class ChapterJumpEntities{

  sealed class AppLevel : ChapterJumpEntities() {
    object ToChapter1Kotlin : AppLevel()
    object ToChapter2Android : AppLevel()
  }
  sealed class Chapter1KotlinGuide : ChapterJumpEntities() {
    object Lesson1ExtendFunction : Chapter1KotlinGuide()
    object Lesson2Coroutine : Chapter1KotlinGuide()
  }

}
