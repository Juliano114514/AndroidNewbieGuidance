package com.example.chapter3_compose_ui.ui.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chapter3_compose_ui.ui.Dimen
import com.example.chapter3_compose_ui.ui.theme.AppTheme
import com.example.chapter3_compose_ui.ui.theme.ThemeColorPreset
import com.example.chapter3_compose_ui.ui.theme.ThemeColorPresets
import com.example.foundation.R
import com.example.foundation.theme.AppThemeMode

object CustomSettingToolbars {

  // 介于 SpacingHalf 与 SpacingSmall，用于标签与控件间距
  private val LabelContentGap = Dimen.SpacingHalf + Dimen.SpacingHalf / 2

  @Composable
  fun LabelText(string: String) {
    Text(
      text = string,
      modifier = Modifier.padding(bottom = LabelContentGap),
      color = colorResource(R.color.text_secondary),
      style = MaterialTheme.typography.labelLarge,
      fontWeight = FontWeight.Bold,
    )
  }

  @Composable
  fun LightDarkSelectToolbar(
    currentMode: AppThemeMode,
    onModeSelected: (AppThemeMode) -> Unit,
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          top = Dimen.SpacingNormal,
          start = Dimen.ScreenHorizontal,
          end = Dimen.ScreenHorizontal,
        ),
    ) {
      LabelText("深色模式")
      Row(horizontalArrangement = Arrangement.spacedBy(Dimen.ItemSpacing)) {
        LightDarkItem(
          modifier = Modifier.weight(1f),
          title = "跟随系统",
          isSelected = currentMode is AppThemeMode.FollowSystem,
          onClick = { onModeSelected(AppThemeMode.FollowSystem) },
        )
        LightDarkItem(
          modifier = Modifier.weight(1f),
          title = "亮色模式",
          isSelected = currentMode is AppThemeMode.Light,
          onClick = { onModeSelected(AppThemeMode.Light) },
        )
        LightDarkItem(
          modifier = Modifier.weight(1f),
          title = "暗色模式",
          isSelected = currentMode is AppThemeMode.Dark,
          onClick = { onModeSelected(AppThemeMode.Dark) },
        )
      }
    }
  }

  @Composable
  private fun LightDarkItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
  ) {
    val selectBackgroundColor: @Composable (Boolean) -> Color = { selected ->
      if (selected) MaterialTheme.colorScheme.primaryContainer
      else MaterialTheme.colorScheme.surfaceVariant
    }

    val selectBorder: @Composable (Boolean) -> BorderStroke? = { selected ->
      if (selected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null
    }

    Surface(
      onClick = onClick,
      modifier = modifier.height(50.dp),
      shape = RoundedCornerShape(Dimen.ItemSpacing),
      color = selectBackgroundColor(isSelected),
      border = selectBorder(isSelected),
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(
          text = title,
          color = MaterialTheme.colorScheme.onPrimaryContainer,
          style = MaterialTheme.typography.labelLarge,
          fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal,
        )
      }
    }
  }

  @Composable
  fun CustomizeColorToolbar(
    selectedPresetId: Int,
    onColorSelected: (ThemeColorPreset) -> Unit,
    modifier: Modifier = Modifier,
  ) {
    Column(
      modifier = modifier
        .fillMaxWidth()
        .padding(Dimen.SpacingNormal),
    ) {
      LabelText("主题颜色(仅Chapter3往后支持)")

      LazyRow(
        modifier = modifier
          .fillMaxWidth()
          .padding(vertical = LabelContentGap),
        horizontalArrangement = Arrangement.spacedBy(Dimen.ItemSpacing),
        contentPadding = PaddingValues(horizontal = LabelContentGap),
      ) {
        items(
          items = ThemeColorPresets.presetColors,
          key = { it.id },
        ) { preset ->
          ColorItem(
            preset = preset,
            isSelected = preset.id == selectedPresetId,
            onClick = { onColorSelected(preset) },
          )
        }
      }
    }
  }

  @Composable
  private fun ColorItem(
    preset: ThemeColorPreset,
    isSelected: Boolean,
    onClick: () -> Unit,
  ) {
    val borderColor =
      if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

    Box(
      modifier = Modifier
        .size(48.dp)
        .clip(CircleShape)
        .border(width = 2.dp, color = borderColor, shape = CircleShape)
        .clickable(onClick = onClick),
      contentAlignment = Alignment.Center,
    ) {
      val innerSize =
        if(preset.isSystemDefault) 40.dp
        else 36.dp

      val background =
        if (preset.isSystemDefault) MaterialTheme.colorScheme.primaryContainer
        else preset.colors.primary

      Box(
        modifier = Modifier
          .size(innerSize)
          .clip(CircleShape)
          .background(background),
        contentAlignment = Alignment.Center,
      ) {
        if(preset.isSystemDefault){
          Text(
            text = "原",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.ExtraBold,
          )
        }
      }
    }
  }
}

@Preview(showSystemUi = true)
@Composable
private fun LightDarkPreview() {
  AppTheme {
    Column {
      CustomSettingToolbars.LightDarkSelectToolbar(
        currentMode = AppThemeMode.FollowSystem,
        onModeSelected = {},
      )

      CustomSettingToolbars.CustomizeColorToolbar(
        selectedPresetId = 0,
        onColorSelected = {},
        modifier = Modifier.fillMaxWidth(),
      )
    }
  }
}
