package com.example.androidnewbieguidance

import android.os.Bundle
import com.example.androidnewbieguidance.databinding.MainLayoutBinding
import com.example.androidnewbieguidance.presenters.chapterLoading.ChapterLoadingPresenter
import com.example.foundation.base.BaseActivity
import com.example.foundation.base.presenter.BasePresenter

class MainActivity : BaseActivity() {

  private lateinit var binding: MainLayoutBinding
  private val mPresenter = BasePresenter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = MainLayoutBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mPresenter.apply{
      add(ChapterLoadingPresenter())
    }

    mPresenter.onBind(this, binding.root)
  }

  override fun onDestroy() {
    super.onDestroy()
    mPresenter.onDestroy()
  }
}