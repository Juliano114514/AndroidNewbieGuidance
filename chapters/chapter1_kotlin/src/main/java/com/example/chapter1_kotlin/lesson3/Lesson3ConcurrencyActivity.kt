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

    // 用 TabLayoutMediator 绑定 TabLayout 和 ViewPager2
    // lambda闭包为 tab参数配置策略 源码附在下方
    TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
      tab.text = types[position].title
    }.attach()

    /**
     * public interface TabConfigurationStrategy {
        * 用于对指定位置页面所对应的标签（Tab）进行配置。通常会调用 TabLayout.Tab#setText(CharSequence) 方法，但也可以应用任何形式的样式定制。
        * @param tab：需要进行配置的标签对象，用以展示数据集中对应位置条目的标题。
        * @param position：该条目在适配器数据集中的位置。
     *  void onConfigureTab(@NonNull TabLayout.Tab tab, int position);
     * }
     */

  }

}