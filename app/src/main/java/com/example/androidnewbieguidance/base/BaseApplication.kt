package com.example.androidnewbieguidance.base

import android.app.Application
import com.example.androidnewbieguidance.di.appModule
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
    }

  }

  private fun initUtils(){
    ToastUtil.init(this);
  }
}