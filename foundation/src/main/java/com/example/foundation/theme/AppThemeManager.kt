package com.example.foundation.theme

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AppThemeManager {

  private const val PREF_NAME = "app_theme"
  private const val KEY_MODE = "night_mode" // 存 name: FollowSystem / Light / Dark

  private lateinit var appContext: Context
  private val _mode = MutableStateFlow<AppThemeMode>(AppThemeMode.FollowSystem)
  val mode: StateFlow<AppThemeMode> = _mode.asStateFlow()

  fun init(context: Context) {
    appContext = context.applicationContext
    //_mode.value = readPersisted()
    applyToDelegate(_mode.value, fromColdStart = true)
  }

  fun setMode(mode: AppThemeMode) {
    if (_mode.value == mode) return
    //persist(mode)
    _mode.value = mode
    applyToDelegate(mode, fromColdStart = false)
  }

  private fun applyToDelegate(mode: AppThemeMode, fromColdStart: Boolean) {
    val nightMode = when (mode) {
      AppThemeMode.FollowSystem ->
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
      AppThemeMode.Light ->
        AppCompatDelegate.MODE_NIGHT_NO
      AppThemeMode.Dark ->
        AppCompatDelegate.MODE_NIGHT_YES
    }
    // 与当前一致则跳过，避免无意义 recreate
    if (AppCompatDelegate.getDefaultNightMode() != nightMode) {
      AppCompatDelegate.setDefaultNightMode(nightMode)
    }
    // setDefaultNightMode 会对已存在的 DayNight Activity 触发 recreate（冷启动时通常还没有 Activity）
  }
  // readPersisted / persist：SharedPreferences 即可；日后换 MMKV 只改这两处
}