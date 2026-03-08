package com.example.foundation.utils.chapterLoading

import com.example.foundation.base.presenter.BasePresenter
import com.example.foundation.utils.chapterLoading.adapter.ChapterAdapter
import com.example.foundation.utils.chapterLoading.adapter.ChapterItem
import com.example.kn_shared.bridge.Action
import com.example.kn_shared.bridge.ChapterBridge

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/26 10:19
 * @Description:
 */
open class BaseChapterNaviPresenter (
  private val chapterBridge: ChapterBridge
) : BasePresenter() {

  protected lateinit var mAdapter: ChapterAdapter

  protected open fun initAdapter(){
    mAdapter = ChapterAdapter { target ->
      mRootView?.context?.let{
        val action = Action.ChapterJumpAction(target)
        chapterBridge.handleAction(action)
      }
    }
  }

  protected open fun getList() : List<ChapterItem> {
    return emptyList()
  }

  protected open fun refreshAdapterData() {
    if (::mAdapter.isInitialized) { // 检查Adapter是否初始化，避免未初始化调用
      mAdapter.refreshList(getList())
    }
  }

  protected open fun refreshAdapterData(list : List<ChapterItem>) {
    if (::mAdapter.isInitialized) { // 检查Adapter是否初始化，避免未初始化调用
      mAdapter.refreshList(list)
    }
  }
}