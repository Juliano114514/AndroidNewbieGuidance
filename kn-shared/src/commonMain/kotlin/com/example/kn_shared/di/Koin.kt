package com.example.kn_shared.di

import com.example.kn_shared.bridge.ChapterBridge
import com.example.kn_shared.utils.chapterJump.NavigationManager
import org.koin.dsl.module

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/21 18:40
 * @Description:
 */

val commonModule = module{
  single { NavigationManager() }
  single { ChapterBridge() }
}