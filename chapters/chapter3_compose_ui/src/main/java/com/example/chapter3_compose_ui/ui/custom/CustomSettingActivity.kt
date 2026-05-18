package com.example.chapter3_compose_ui.ui.custom

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.chapter3_compose_ui.ui.custom.CustomSettingToolbars.CustomizeColorToolbar
import com.example.chapter3_compose_ui.ui.custom.CustomSettingToolbars.LightDarkSelectToolbar
import com.example.chapter3_compose_ui.ui.theme.AppTheme
import com.example.foundation.base.BaseActivity

class CustomSettingActivity : BaseActivity() {
  private val viewModel: CustomSettingViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val currentMode by viewModel.themeMode.collectAsState()
      val selectedPresetId by viewModel.selectedPresetId.collectAsState()
      AppTheme(darkTheme = isSystemInDarkTheme()) {
        Surface(
          modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
          color = MaterialTheme.colorScheme.background
        ){
          Column{
            LightDarkSelectToolbar(
              currentMode = currentMode,
              onModeSelected = { mode ->
                viewModel.updateThemeMode(mode)
              }
            )

            CustomizeColorToolbar(
              selectedPresetId = selectedPresetId,
              onColorSelected = viewModel::updateColorPreset,
              modifier = Modifier.fillMaxWidth(),
            )
          }
        }
      }
    }
  }
}