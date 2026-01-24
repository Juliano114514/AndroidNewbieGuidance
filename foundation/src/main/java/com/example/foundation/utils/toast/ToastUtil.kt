package com.example.foundation.utils.toast

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.foundation.R

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/22 19:10
 * @Description:
 */
object ToastUtil {
  private val mainHandler = Handler(Looper.getMainLooper())
  private var mToast: Toast? = null
  private lateinit var mAppContext: Context

  fun init(application: Application) {
    mAppContext = application.applicationContext
  }

  fun showToast(text: String) {
    val info = ToastInfo(text = text)
    checkAndToast(info)
  }

  fun showToast(info: ToastInfo) {
    checkAndToast(info)
  }

  fun showSuccess(text: String) {
    val info = ToastInfo(text = text, type = ToastType.SUCCESS)
    checkAndToast(info)
  }

  fun showWarn(text: String) {
    val info = ToastInfo(text = text, type = ToastType.WARN)
    checkAndToast(info)
  }

  fun showFail(text: String) {
    val info = ToastInfo(text = text, type = ToastType.FAIL)
    checkAndToast(info)
  }


  private fun checkAndToast(info : ToastInfo){
    if(info.text.isBlank()) return
    if (!::mAppContext.isInitialized) {
      throw UninitializedPropertyAccessException("ToastUtil未初始化")
    }

    if(Looper.myLooper() != Looper.getMainLooper()){
      mainHandler.post { checkAndToast(info) }
      return
    }

    doRealToast(info)
  }

  @SuppressLint("InflateParams")
  private fun doRealToast(info : ToastInfo){
    mToast?.cancel()
    mToast = Toast(mAppContext);


    val toastView = LayoutInflater.from(mAppContext).inflate(R.layout.toast_custom, null)
    val ivIcon = toastView.findViewById<ImageView>(R.id.iv_toast_icon)
    val tvText = toastView.findViewById<TextView>(R.id.tv_toast_text)

    tvText.text = info.text

    val targetIconRes = info.iconRes ?: info.type.iconRes
    if(targetIconRes != 0){
      ivIcon.isVisible = true
      ivIcon.setImageResource(targetIconRes)
    } else{
      ivIcon.isVisible = false
    }

    mToast?.apply {
      view = toastView
      duration = info.duration
      setGravity(info.gravity.grav, 0,getGravityOffset(info.gravity))
    }

    mToast?.show()
  }

  private fun getGravityOffset(gravity: ToastGravity): Int {
    val dpOffset = when (gravity) {
      ToastGravity.TOP -> 60
      ToastGravity.CENTER -> 0
      ToastGravity.BOTTOM -> 60
    }
    // dp转px：适配不同屏幕密度
    // TODO: dp 改为拓展函数 Util
    val density = mAppContext.resources.displayMetrics.density
    return (dpOffset * density).toInt()
  }

  fun cancelToast() {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      mToast?.cancel()
    } else {
      mainHandler.post { mToast?.cancel() }
    }
  }
}

object ToastBuilder{}

data class ToastInfo(
  var text : String = "",
  var type : ToastType = ToastType.NONE,
  var gravity : ToastGravity = ToastGravity.CENTER,
  var iconRes : Int? = null,  // 可定制icon，或者使用type默认图标
  var duration : Int = Toast.LENGTH_SHORT
)

enum class ToastType(val iconRes: Int) {
  SUCCESS(R.drawable.round_success),
  FAIL(R.drawable.round_error),
  WARN(R.drawable.round_warning),
  NONE(0)
}

enum class ToastGravity(val grav : Int){
  BOTTOM(Gravity.BOTTOM),
  CENTER(Gravity.CENTER),
  TOP(Gravity.TOP)
}