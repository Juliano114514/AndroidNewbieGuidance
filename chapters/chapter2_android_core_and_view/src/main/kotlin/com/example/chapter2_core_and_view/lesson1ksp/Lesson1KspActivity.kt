package com.example.chapter2_core_and_view.lesson1ksp

import android.os.Bundle
import com.example.chapter2_core_and_view.databinding.Lesson1KspLayoutBinding
import com.example.foundation.base.BaseActivity
import com.example.foundation.base.presenter.BasePresenter

class Lesson1KspActivity : BaseActivity() {
  private lateinit var binding: Lesson1KspLayoutBinding
  private val mPresenter = BasePresenter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = Lesson1KspLayoutBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mPresenter.add(Lesson1KspPresenter())
    mPresenter.onBind(this, binding.root)
  }

  override fun onDestroy() {
    super.onDestroy()
    mPresenter.onDestroy()
  }
}
