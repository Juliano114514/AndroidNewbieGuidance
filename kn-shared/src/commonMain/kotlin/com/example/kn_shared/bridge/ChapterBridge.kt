package com.example.kn_shared.bridge

import com.example.kn_shared.utils.chapterNavi.NavigationManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/21 17:40
 * @Description:
 */
class ChapterBridge : KoinComponent {

  private val navigationManager : NavigationManager by inject()

  fun handleAction(action: Action){
    when(action){
      is Action.ChapterJumpAction -> {
        navigationManager.navigateTo(action.chapterDetail)
      }
    }
  }

}