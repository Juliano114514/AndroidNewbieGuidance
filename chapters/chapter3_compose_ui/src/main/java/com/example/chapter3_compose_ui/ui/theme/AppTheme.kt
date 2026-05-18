package com.example.chapter3_compose_ui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun AppTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorSource by AppThemeColorManager.colorSource.collectAsState()
  val colorScheme = resolveAppColorScheme(colorSource, darkTheme)

  MaterialTheme(
    colorScheme = colorScheme,
    content = content,
  )
}
