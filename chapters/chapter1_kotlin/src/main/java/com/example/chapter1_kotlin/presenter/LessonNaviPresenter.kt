package com.example.chapter1_kotlin.presenter

import androidx.recyclerview.widget.GridLayoutManager
import com.example.foundation.databinding.NavigationLayoutBinding
import com.example.foundation.utils.chapterLoading.BaseChapterNaviPresenter
import com.example.foundation.utils.chapterLoading.adapter.ChapterItem
import com.example.kn_shared.bridge.ChapterBridge
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/26 10:58
 * @Description:
 */
class LessonNaviPresenter(
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
      ChapterItem(name = "Lesson1\n拓展函数", target = ChapterNaviEntities.Chapter1KotlinGuide.Lesson2ExtendFunction)
    )
  }


  override fun onUnbindView() {}
}