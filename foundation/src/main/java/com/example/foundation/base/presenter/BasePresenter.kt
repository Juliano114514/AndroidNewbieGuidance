package com.example.foundation.base.presenter

import android.view.View
import com.example.foundation.base.BaseActivity
import java.lang.ref.WeakReference

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/31 16:17
 * @Description:
 */
open class BasePresenter : IPresenter {

  private var mActivityRef : WeakReference<BaseActivity>? = null
  private var mRootViewRef : WeakReference<View>? = null

  protected val mActivity: BaseActivity?
    get() = mActivityRef?.get()

  protected val mRootView: View?
    get() = mRootViewRef?.get()

  private val childPresenters = mutableListOf<IPresenter>()

  override fun onBind(activity: BaseActivity, rootView: View) {
    mActivityRef = WeakReference(activity)
    mRootViewRef = WeakReference(rootView)
    childPresenters.forEach { it.onBind(activity, rootView) }
    onBindView()
  }
  override fun onDestroy() {
    mActivityRef?.clear()
    mActivityRef = null
    mRootViewRef?.clear()
    mRootViewRef = null
    childPresenters.forEach { it.onDestroy() }
    childPresenters.clear()
    onUnbindView()
  }

  open fun onBindView(){}
  open fun onUnbindView() {}

  fun add(presenter: IPresenter){
    childPresenters.add(presenter)
  }

  protected fun <T : View> findViewById(id: Int): T? {
    return mRootView?.findViewById<T>(id)
  }
}