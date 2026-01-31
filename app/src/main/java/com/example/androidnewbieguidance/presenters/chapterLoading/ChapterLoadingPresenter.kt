package com.example.androidnewbieguidance.presenters.chapterLoading

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidnewbieguidance.R
import com.example.androidnewbieguidance.presenters.chapterLoading.adapter.ChapterAdapter
import com.example.androidnewbieguidance.presenters.chapterLoading.adapter.ChapterItem
import com.example.foundation.base.presenter.BasePresenter
import com.example.foundation.entities.ChapterEntities

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/31 16:24
 * @Description: 处理章节跳转业务
 */
class ChapterLoadingPresenter : BasePresenter() {

  override fun onBindView() {
    val recyclerView = findViewById<RecyclerView>(R.id.rv_chapter_list)
    recyclerView?.layoutManager = GridLayoutManager(mRootView?.context, 3)

    val adapter = ChapterAdapter { target ->
      mRootView?.context?.let{
        ChapterJumpProxy.jumpTo(it,target)
      }
    }
    recyclerView?.adapter = adapter

    val dataList = getList()
    adapter.refreshList(dataList)
  }

  private fun getList() : List<ChapterItem>{
    return listOf(
      ChapterItem(name = "第一章\nkotlin入门", target = ChapterEntities.Chapter1Kotlin)
    )
  }


  override fun onUnbindView() {}
}