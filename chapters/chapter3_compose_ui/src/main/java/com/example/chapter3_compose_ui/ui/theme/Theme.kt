package com.example.chapter3_compose_ui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.foundation.R

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val base = if (darkTheme) darkColorScheme() else lightColorScheme()
    val colorScheme = base.copy(
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
    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
