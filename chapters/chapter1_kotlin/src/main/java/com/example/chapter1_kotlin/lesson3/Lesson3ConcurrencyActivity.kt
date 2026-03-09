package com.example.chapter1_kotlin.lesson3

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chapter1_kotlin.databinding.Lesson3ConcurrencyLayoutBinding
import com.example.chapter1_kotlin.lesson3.fragments.ConcurrencyFragment
import com.example.foundation.base.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @Author: JULIANO
 * @CreateDate: 2026/3/9 15:12
 * @Description:
 */
class Lesson3ConcurrencyActivity : BaseActivity() {
  private lateinit var binding: Lesson3ConcurrencyLayoutBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = Lesson3ConcurrencyLayoutBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initView()
  }

  private fun initView(){
    val types = ConcurrencyType.entries.toTypedArray()
    binding.viewPager.adapter = object : FragmentStateAdapter(this) {
      override fun createFragment(position: Int): Fragment {
        return ConcurrencyFragment.newInstance(types[position])
      }

      override fun getItemCount(): Int = types.size
    }

    TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
      tab.text = types[position].title
    }.attach()
  }

}