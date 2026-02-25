package com.example.foundation.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kn_shared.utils.chapterJump.ChapterJumpEntities
import com.example.kn_shared.utils.chapterJump.NavigationManager
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/24 13:43
 * @Description:
 */
open class BaseActivity : ComponentActivity() {

  private val navigationManager : NavigationManager by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED){
        navigationManager.navEvents.collect { entity ->
          onHandleNavigation(entity)
        }
      }
    }
  }

  open fun onHandleNavigation(entity: ChapterJumpEntities): Boolean {
    return false
  }
}