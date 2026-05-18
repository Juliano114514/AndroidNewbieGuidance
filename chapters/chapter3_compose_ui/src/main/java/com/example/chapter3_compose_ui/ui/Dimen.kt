package com.example.chapter3_compose_ui.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Compose 语义间距，与 foundation dimen.xml 同名资源一一对应；基准 4dp 网格
object Dimen {

  val ScreenHorizontal: Dp = 16.dp // 屏幕左右内边距
  val ScreenVertical: Dp = 16.dp // 屏幕上下内容边距（列表底部、页面留白等）
  val ItemSpacing: Dp = 12.dp // 列表项、同行控件之间的间距
  val SpacingHalf: Dp = 4.dp // 紧凑半间距（标签与内容、细分割线、微 Spacer）
  val SpacingSmall: Dp = 8.dp // 小间距（卡片内边、次要分隔；约为 SpacingNormal 的一半）
  val SpacingNormal: Dp = 16.dp // 标准区块上下间距
  val SpacingLarge: Dp = 24.dp // 大区块、表单区块之间的间距
  val SectionTop: Dp = 32.dp // 顶部区域、标题区与状态栏下方的留白
}
