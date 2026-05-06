package com.example.chapter1_kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.foundation.base.BaseActivity
import com.example.foundation.base.presenter.BasePresenter
import com.example.foundation.databinding.NavigationLayoutBinding
import com.example.kn_shared.bridge.ChapterBridge
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities
import com.example.chapter1_kotlin.presenter.LessonNaviPresenter
import com.example.chapter1_kotlin.presenter.lessonNavi.Chapter1KotlinLessonNaviProxy
import org.koin.android.ext.android.inject
import kotlin.getValue

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/22 16:31
 * @Description:
 */
class LessonKotlinActivity : BaseActivity() {
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
      add(LessonNaviPresenter(chapterBridge, binding))
    }

    mPresenter.onBind(this, binding.root)
  }

  override fun onHandleNavigation(entity: ChapterNaviEntities): Boolean {
    return if(entity is ChapterNaviEntities.Chapter1KotlinGuide)
      Chapter1KotlinLessonNaviProxy.jumpTo(this, entity)
    else
      false
  }
}