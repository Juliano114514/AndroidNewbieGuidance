package com.example.foundation.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.foundation.utils.logger.Logger
import com.example.foundation.utils.logger.LoggerTagConstance.CHAPTER_NAVIGATION
import com.example.foundation.utils.toast.ToastUtil
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities
import com.example.kn_shared.utils.chapterNavi.NavigationManager
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/24 13:43
 * @Description:
 */
open class BaseActivity : AppCompatActivity() {

  private val navigationManager : NavigationManager by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.RESUMED){
        navigationManager.navEvents.collect { entity ->
          Logger.d(CHAPTER_NAVIGATION, "收到路由事件: ${entity.javaClass.simpleName}")
          val flag = onHandleNavigation(entity)
          if(!flag){
            val errorMsg = "路由处理失败: ${entity.javaClass.simpleName}"
            Logger.e(CHAPTER_NAVIGATION, errorMsg)
            ToastUtil.showFail("当前路由${entity.javaClass.simpleName}非法或不匹配，请检查代码！")
          }
        }
      }
    }
  }

  open fun onHandleNavigation(entity: ChapterNaviEntities): Boolean {
    return false
  }
}