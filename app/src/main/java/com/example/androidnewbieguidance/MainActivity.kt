package com.example.androidnewbieguidance

import android.os.Bundle

import com.example.androidnewbieguidance.presenters.mainMenuNavigating.MainMenuNaviProxy
import com.example.androidnewbieguidance.presenters.mainMenuNavigating.MainMenuNaviPresenter
import com.example.foundation.base.BaseActivity
import com.example.foundation.base.presenter.BasePresenter
import com.example.foundation.databinding.NavigationLayoutBinding
import com.example.kn_shared.bridge.ChapterBridge
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

  private lateinit var binding: NavigationLayoutBinding
  private val mPresenter = BasePresenter()

  private val chapterBridge: ChapterBridge by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = NavigationLayoutBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mPresenter.apply{
      add(MainMenuNaviPresenter(chapterBridge, binding))
    }

    mPresenter.onBind(this, binding.root)
  }

  override fun onHandleNavigation(entity: ChapterNaviEntities): Boolean {
    return if(entity is ChapterNaviEntities.MainMenu)
      MainMenuNaviProxy.jumpTo(this,entity)
    else
      false
  }

  override fun onDestroy() {
    super.onDestroy()
    mPresenter.onDestroy()
  }
}