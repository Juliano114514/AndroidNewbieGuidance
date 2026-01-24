package com.example.foundation.utils.logger

import android.util.Log


/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/22 19:10
 * @Description: 基础封装Logger，之后另说😋
 */
object Logger {

  /**
   * v - Verbose 详细信息
   * d - Debug   调试信息
   * i - Info    基础信息
   * w - Warn    警告信息
   * e - Error   错误信息
  */

  private const val DEFAULT_TAG = "Logger"

  fun v(
    tag: String? = null,
    msg: String? = null,
    tr: Throwable? = null
  ) {
    val logTag = tag ?: DEFAULT_TAG
    val logMsg = msg ?: ""

    if(logMsg.isNotEmpty() || tr != null){
      Log.v(logTag, logMsg, tr)
    }
  }


  fun d(
    tag: String? = null,
    msg: String? = null,
    tr: Throwable? = null
  ) {
    val logTag = tag ?: DEFAULT_TAG
    val logMsg = msg ?: ""

    if(logMsg.isNotEmpty() || tr != null){
      Log.d(logTag, logMsg, tr)
    }
  }


  fun i(
    tag: String? = null,
    msg: String? = null,
    tr: Throwable? = null
  ) {
    val logTag = tag ?: DEFAULT_TAG
    val logMsg = msg ?: ""

    if(logMsg.isNotEmpty() || tr != null){
      Log.i(logTag, logMsg, tr)
    }
  }

  fun w(
    tag: String? = null,
    msg: String? = null,
    tr: Throwable? = null
  ) {
    val logTag = tag ?: DEFAULT_TAG
    val logMsg = msg ?: ""

    if(logMsg.isNotEmpty() || tr != null){
      Log.w(logTag, logMsg, tr)
    }
  }

  fun e(
    tag: String? = null,
    msg: String? = null,
    tr: Throwable? = null
  ) {
    val logTag = tag ?: DEFAULT_TAG
    val logMsg = msg ?: ""

    if(logMsg.isNotEmpty() || tr != null){
      Log.e(logTag, logMsg, tr)
    }
  }
}