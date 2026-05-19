package com.example.androidnewbieguidance.base

import android.app.Application
import com.example.androidnewbieguidance.di.appModule
import com.example.chapter2_core_and_view.lesson2room.di.roomModule
import com.example.chapter3_compose_ui.ui.theme.AppThemeColorManager
import com.example.foundation.theme.AppThemeManager
import com.example.foundation.utils.toast.ToastUtil
import com.example.kn_shared.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/30 17:01
 * @Description:
 */
class BaseApplication : Application(){
  override fun onCreate() {
    super.onCreate()
    initUtils()
    startKoin {
      androidContext(this@BaseApplication)
      modules(commonModule, appModule)
      modules(roomModule)
    }

  }

  private fun initUtils(){
    ToastUtil.init(this)
    AppThemeManager.init(this)
    AppThemeColorManager.init(this)
  }
}