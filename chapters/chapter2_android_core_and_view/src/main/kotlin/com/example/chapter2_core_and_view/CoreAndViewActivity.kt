package com.example.chapter2_core_and_view

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.chapter2_core_and_view.lessonnavi.CoreAndViewNaviPresenter
import com.example.chapter2_core_and_view.lessonnavi.CoreAndViewNaviProxy
import com.example.foundation.base.BaseActivity
import com.example.foundation.base.presenter.BasePresenter
import com.example.foundation.databinding.NavigationLayoutBinding
import com.example.kn_shared.bridge.ChapterBridge
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities
import org.koin.android.ext.android.inject
import kotlin.getValue

class CoreAndViewActivity : BaseActivity() {
  private lateinit var binding: NavigationLayoutBinding
  private val mPresenter = BasePresenter()

  private val chapterBridge: ChapterBridge by inject()

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = NavigationLayoutBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.tvTitle.text = "Chapter1 Kotlin基础"

    mPresenter.apply{
      add(CoreAndViewNaviPresenter(chapterBridge, binding))
    }

    mPresenter.onBind(this, binding.root)
  }

  override fun onHandleNavigation(entity: ChapterNaviEntities): Boolean {
    return if(entity is ChapterNaviEntities.Chapter2AndroidGuide)
      CoreAndViewNaviProxy.jumpTo(this, entity)
    else
      false
  }
}