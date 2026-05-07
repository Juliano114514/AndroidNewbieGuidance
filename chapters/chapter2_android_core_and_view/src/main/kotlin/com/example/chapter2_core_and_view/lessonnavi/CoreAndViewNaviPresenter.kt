package com.example.chapter2_core_and_view.lessonnavi

import androidx.recyclerview.widget.GridLayoutManager
import com.example.foundation.databinding.NavigationLayoutBinding
import com.example.foundation.utils.chapterLoading.BaseChapterNaviPresenter
import com.example.foundation.utils.chapterLoading.adapter.ChapterItem
import com.example.kn_shared.bridge.ChapterBridge
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities

class CoreAndViewNaviPresenter(
  chapterBridge: ChapterBridge,
  private val binding: NavigationLayoutBinding
) : BaseChapterNaviPresenter(chapterBridge){

  override fun onBindView() {
    val recyclerView = binding.rvChapterList
    recyclerView.layoutManager = GridLayoutManager(mRootView?.context, 3)

    initAdapter()

    recyclerView.adapter = mAdapter
    refreshAdapterData(getList())
  }

  override fun getList() : List<ChapterItem>{
    return listOf(
      ChapterItem(
        name = "Lesson1\nKSP工具",
        target = ChapterNaviEntities.Chapter2AndroidGuide.Lesson1Ksp),
    )
  }

  override fun onUnbindView() {}
}