package com.example.chapter3_compose_ui.ui.theme

import androidx.compose.ui.graphics.Color

/** 预设 id：0 系统默认；1–7 彩色；8–9 中性强调色（第三期壁纸取色不走此表）。 */
object ThemeColorPresetIds {
  const val SYSTEM_DEFAULT = 0
}

/** 主题色来源：系统动态 / 预设 / 壁纸（壁纸取色第三期接入）。 */
sealed interface ThemeColorSource {
  /** 设置页色块选中 id；[Wallpaper] 在取色 UI 完成前回落为系统动态。 */
  val uiPresetId: Int
    get() = when (this) {
      is Preset -> presetId
      else -> ThemeColorPresetIds.SYSTEM_DEFAULT
    }

  data object SystemDynamic : ThemeColorSource
  data class Preset(val presetId: Int) : ThemeColorSource
  data class Wallpaper(
    val imageUri: String,
    val seedColorArgb: Long? = null,
  ) : ThemeColorSource
}

/**
 * 与 foundation `theme_primary` / `theme_primary_dark` / `theme_primary_light` 对齐。
 * 定制主题在深浅色切换时，仅 [primaryLight] / [primaryLightNight]（对应 `theme_primary_light`）随模式变化。
 */
data class ThemePrimaryColors(
  val primary: Color,
  val primaryDark: Color,
  val primaryLight: Color,
  val primaryLightNight: Color,
) {
  fun primaryContainerFor(darkTheme: Boolean): Color =
    if (darkTheme) primaryLightNight else primaryLight

  companion object {
    val Unspecified = ThemePrimaryColors(
      primary = Color.Unspecified,
      primaryDark = Color.Unspecified,
      primaryLight = Color.Unspecified,
      primaryLightNight = Color.Unspecified,
    )
  }
}

data class ThemeColorPreset(
  val id: Int,
  val name: String,
  val colors: ThemePrimaryColors,
) {
  val isSystemDefault: Boolean get() = id == ThemeColorPresetIds.SYSTEM_DEFAULT
}

object ThemeColorPresets {
  val defaultColor = ThemeColorPreset(
    id = ThemeColorPresetIds.SYSTEM_DEFAULT,
    name = "System Default",
    colors = ThemePrimaryColors.Unspecified,
  )

  val presetColors: List<ThemeColorPreset> = listOf(
    defaultColor,
    preset(1, "Red", 0xFFDC3030, 0xFFB82626, 0xF0FBE8E8, 0xD93D1A1A),
    preset(2, "Orange", 0xFFDC7700, 0xFFB86200, 0xF0FBF0E5, 0xD93D2A1A),
    preset(3, "Yellow", 0xFFDBC000, 0xFFB8A200, 0xF0FBFAE5, 0xD93D3A1A),
    preset(4, "Green", 0xFF00DC5A, 0xFF00B84C, 0xF0E5FBF2, 0xD91A3D2A),
    preset(5, "Blue", 0xFF007CDC, 0xFF0066B8, 0xF0E5F0FB, 0xD91A2A3D),
    preset(6, "Indigo", 0xFF2F55DC, 0xFF2644B8, 0xF0E8EBFB, 0xD91A1A3D),
    preset(7, "Purple", 0xFF9F2FDC, 0xFF8226B8, 0xF0F0E8FB, 0xD92A1A3D),
  )

  fun byId(id: Int): ThemeColorPreset? = presetColors.find { it.id == id }

  private fun preset(
    id: Int,
    name: String,
    primary: Long,
    primaryDark: Long,
    primaryLight: Long,
    primaryLightNight: Long,
  ) = ThemeColorPreset(
    id = id,
    name = name,
    colors = ThemePrimaryColors(
      primary = Color(primary),
      primaryDark = Color(primaryDark),
      primaryLight = Color(primaryLight),
      primaryLightNight = Color(primaryLightNight),
    ),
  )
}
