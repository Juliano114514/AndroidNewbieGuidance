package com.example.foundation.theme
sealed class AppThemeMode {
  data object FollowSystem : AppThemeMode()
  data object Light : AppThemeMode()
  data object Dark : AppThemeMode()
}