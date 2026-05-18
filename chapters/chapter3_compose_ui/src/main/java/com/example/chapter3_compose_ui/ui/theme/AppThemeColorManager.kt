package com.example.chapter3_compose_ui.ui.theme

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AppThemeColorManager {
  private const val PREF_NAME = "app_theme_color"
  private const val KEY_SOURCE_TYPE = "source_type" // System / Preset / Wallpaper
  private const val KEY_PRESET_ID = "preset_id"
  private const val KEY_WALLPAPER_URI = "wallpaper_uri"

  private lateinit var appContext: Context

  private val _colorSource = MutableStateFlow<ThemeColorSource>(ThemeColorSource.SystemDynamic)
  val colorSource: StateFlow<ThemeColorSource> = _colorSource.asStateFlow()

  fun init(context: Context) {
    appContext = context.applicationContext
  }

  fun setWallpaper(imageUri: String, seedColorArgb: Long? = null) {
    setSource(ThemeColorSource.Wallpaper(imageUri, seedColorArgb))
  }

  fun setPreset(preset: ThemeColorPreset) {
    setSource(
      if (preset.isSystemDefault) ThemeColorSource.SystemDynamic
      else ThemeColorSource.Preset(preset.id),
    )
  }

  private fun setSource(source: ThemeColorSource) {
    if (_colorSource.value == source) return
    _colorSource.value = source
  }
}
