package com.example.androidnewbieguidance

import android.os.Bundle
import com.example.androidnewbieguidance.databinding.MainLayoutBinding
import com.example.androidnewbieguidance.presenters.chapterLoading.ChapterMainMenuJumpProxy
import com.example.androidnewbieguidance.presenters.chapterLoading.ChapterLoadingPresenter
import com.example.foundation.base.BaseActivity
import com.example.foundation.base.presenter.BasePresenter
import com.example.kn_shared.bridge.ChapterBridge
import com.example.kn_shared.utils.chapterJump.ChapterJumpEntities
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

  private lateinit var binding: MainLayoutBinding
  private val mPresenter = BasePresenter()

  private val chapterBridge: ChapterBridge by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = MainLayoutBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mPresenter.apply{
      add(ChapterLoadingPresenter(chapterBridge))
    }

    mPresenter.onBind(this, binding.root)
  }

  override fun onHandleNavigation(entity: ChapterJumpEntities): Boolean {
    if(entity is ChapterJumpEntities.AppLevel)
      return ChapterMainMenuJumpProxy.jumpTo(this,entity)
    else
      return false
  }

  override fun onDestroy() {
    super.onDestroy()
    mPresenter.onDestroy()
  }
}