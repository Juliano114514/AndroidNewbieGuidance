package com.example.foundation.base

import android.app.Application
import com.example.foundation.utils.toast.ToastUtil

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/30 17:01
 * @Description:
 */
class BaseApplication : Application(){
  override fun onCreate() {
    super.onCreate()
    initUtils()
  }

  private fun initUtils(){
    ToastUtil.init(this);
  }
}