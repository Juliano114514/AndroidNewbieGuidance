package com.example.foundation.entities

import android.os.Bundle

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/1 14:58
 * @Description:
 */
sealed class ChapterEntities(open val bundle: Bundle? = null) {
  object Chapter1Kotlin : ChapterEntities()
  object Chapter2AndroidAndJVM : ChapterEntities()
}