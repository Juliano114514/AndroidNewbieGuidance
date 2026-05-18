package com.example.chapter3_compose_ui.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.foundation.R

@Composable
internal fun resolveAppColorScheme(
  source: ThemeColorSource,
  darkTheme: Boolean,
): ColorScheme {
  val base = foundationBaseColorScheme(darkTheme)
  return when (source) {
    is ThemeColorSource.SystemDynamic -> base
    is ThemeColorSource.Preset -> {
      val preset = ThemeColorPresets.byId(source.presetId) ?: ThemeColorPresets.defaultColor
      if (preset.isSystemDefault) base
      else applyThemePrimaryColors(base, preset.colors, darkTheme)
    }
    is ThemeColorSource.Wallpaper -> base
  }
}

internal fun applyThemePrimaryColors(
  baseScheme: ColorScheme,
  colors: ThemePrimaryColors,
  darkTheme: Boolean,
): ColorScheme {
  val primary = colors.primary
  val primaryContainer = colors.primaryContainerFor(darkTheme)
  return baseScheme.copy(
    primary = primary,
    onPrimary = baseScheme.onPrimary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = colors.primaryDark,
    secondary = primary,
    onSecondary = baseScheme.onSecondary,
    secondaryContainer = primaryContainer,
    onSecondaryContainer = colors.primaryDark,
  )
}

@Composable
private fun foundationBaseColorScheme(darkTheme: Boolean): ColorScheme {
  val base = if (darkTheme) darkColorScheme() else lightColorScheme()
  return base.copy(
    primary = colorResource(R.color.theme_primary),
    onPrimary = colorResource(R.color.theme_on_primary),
    primaryContainer = colorResource(R.color.theme_primary_light),
    onPrimaryContainer = colorResource(R.color.theme_primary_dark),
    secondary = colorResource(R.color.theme_primary),
    onSecondary = colorResource(R.color.theme_on_primary),
    secondaryContainer = colorResource(R.color.theme_primary_light),
    onSecondaryContainer = colorResource(R.color.theme_primary_dark),
    background = colorResource(R.color.bg_page),
    onBackground = colorResource(R.color.text_title),
    surface = colorResource(R.color.bg_card),
    onSurface = colorResource(R.color.text_body),
    surfaceVariant = colorResource(R.color.bg_elevated),
    onSurfaceVariant = colorResource(R.color.text_secondary),
    error = colorResource(R.color.status_error),
    onError = colorResource(R.color.theme_on_primary),
    outline = colorResource(R.color.border),
    outlineVariant = colorResource(R.color.divider),
  )
}
