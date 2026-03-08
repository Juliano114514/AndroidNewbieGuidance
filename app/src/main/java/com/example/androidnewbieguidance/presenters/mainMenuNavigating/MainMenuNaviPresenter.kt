package com.example.androidnewbieguidance.presenters.mainMenuNavigating

import androidx.recyclerview.widget.GridLayoutManager
import com.example.foundation.databinding.NavigationLayoutBinding

import com.example.foundation.utils.chapterLoading.adapter.ChapterItem
import com.example.foundation.utils.chapterLoading.BaseChapterNaviPresenter
import com.example.kn_shared.bridge.ChapterBridge
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/31 16:24
 * @Description: 处理章节跳转业务
 */
class MainMenuNaviPresenter(
  chapterBridge: ChapterBridge,
  private val binding: NavigationLayoutBinding
) : BaseChapterNaviPresenter(chapterBridge) {

  override fun onBindView() {
    val recyclerView = binding.rvChapterList
    recyclerView.layoutManager = GridLayoutManager(mRootView?.context, 3)

    initAdapter()

    recyclerView.adapter = mAdapter
    refreshAdapterData(getList())
  }


  override fun getList() : List<ChapterItem>{
    return listOf(
      ChapterItem(name = "第一章\nkotlin入门", target = ChapterNaviEntities.MainMenu.ToChapter1Kotlin)
    )
  }


  override fun onUnbindView() {}
}