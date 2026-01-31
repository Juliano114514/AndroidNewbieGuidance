package com.example.foundation.base.presenter

import android.view.View
import com.example.foundation.base.BaseActivity

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/31 16:09
 * @Description:
 */
interface IPresenter {
  fun onBind(activity: BaseActivity, rootView: View)
  fun onDestroy()
}