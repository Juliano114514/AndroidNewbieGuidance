package com.example.kn_shared.utils.chapterNavi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/25 11:14
 * @Description:
 */
class NavigationManager {
  // extraBufferCapacity 确保事件在页面切换时不会丢失
  private val _navEvents = Channel<ChapterNaviEntities>(Channel.BUFFERED)
  val navEvents = _navEvents.receiveAsFlow()

  fun navigateTo(entity: ChapterNaviEntities) {
    _navEvents.trySend(entity)
  }
}