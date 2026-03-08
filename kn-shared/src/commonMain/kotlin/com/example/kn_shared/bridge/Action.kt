package com.example.kn_shared.bridge

import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/21 17:38
 * @Description: MVI事件相关
 */
sealed class Action {
  data class ChapterJumpAction(
    val chapterDetail : ChapterNaviEntities
  ) : Action()
}