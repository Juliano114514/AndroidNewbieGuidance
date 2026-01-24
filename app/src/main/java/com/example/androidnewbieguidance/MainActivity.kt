package com.example.androidnewbieguidance

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.androidnewbieguidance.databinding.MainLayoutBinding
import com.example.foundation.utils.toast.ToastUtil

class MainActivity : ComponentActivity() {

  private lateinit var binding: MainLayoutBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = MainLayoutBinding.inflate(layoutInflater)
    setContentView(binding.root)

    initListener()
  }

  private fun initListener(){
    binding.test1.setOnClickListener {
      ToastUtil.showToast("你好")
    }

    binding.test2.setOnClickListener {
      ToastUtil.showWarn("我是丁真")
    }

    binding.test3.setOnClickListener {
      ToastUtil.showSuccess("芝士雪豹")
    }

    binding.test4.setOnClickListener {
      ToastUtil.showFail("雪豹闭嘴")
    }
  }

}