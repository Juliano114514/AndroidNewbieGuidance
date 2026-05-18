package com.example.chapter3_compose_ui.ui.custom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapter3_compose_ui.ui.theme.AppThemeColorManager
import com.example.chapter3_compose_ui.ui.theme.ThemeColorPreset
import com.example.foundation.theme.AppThemeManager
import com.example.foundation.theme.AppThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CustomSettingViewModel : ViewModel() {
  // 初始值从 AppThemeManager 读，不要写死 FollowSystem
  private val _themeMode = MutableStateFlow(AppThemeManager.mode.value)
  val themeMode: StateFlow<AppThemeMode> = _themeMode.asStateFlow()

  val selectedPresetId: StateFlow<Int> =
    AppThemeColorManager.colorSource
      .map { it.uiPresetId }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppThemeColorManager.colorSource.value.uiPresetId,
      )

  init {
    viewModelScope.launch {
      AppThemeManager.mode.collect { _themeMode.value = it }
    }
  }

  fun updateThemeMode(mode: AppThemeMode) {
    AppThemeManager.setMode(mode)  // 持久化 + setDefaultNightMode + recreate
  }

  fun updateColorPreset(preset: ThemeColorPreset) {
    AppThemeColorManager.setPreset(preset)
  }
}