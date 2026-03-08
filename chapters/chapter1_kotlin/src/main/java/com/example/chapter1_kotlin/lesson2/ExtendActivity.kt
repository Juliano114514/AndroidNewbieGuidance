package com.example.chapter1_kotlin.lesson2

import android.os.Bundle
import com.example.foundation.base.BaseActivity
import com.example.foundation.utils.logger.Logger
import com.example.foundation.utils.toast.ToastUtil

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/22 16:34
 * @Description: 拓展函数相关内容
 */
class ExtendActivity : BaseActivity() {
  companion object{
    const val TAG = "ExtendFunction"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ToastUtil.showWarn("暂时没做演示，请移步源代码或查看日志")
    test()
  }

  private fun test(){
    var user : UserInfo? = UserInfo().myApply {
      this.userId = "114514"
      userId = "1919810"  // 不用this时需要注意是否存在命名和调用冲突
      Logger.d(TAG,"apply，T.() 调用使用${this.userId}")
    }.myAlso{
      Logger.d(TAG, "also，block(this), 调用使用 ${it.userId}")
    }

    user?.myLet{
      it.userName = "田所 浩二"
      Logger.d(TAG, "let, block(this), 调用使用 ${it.userName}")
      it // lambda末尾为返回值
    }.myRun{
      if(this == null) return@myRun
      this.userStatus = Status.ONLINE
      Logger.d(TAG, "run，T.() 调用使用${this.userStatus.name}")
    }
  }


}

data class UserInfo(
  var userId : String? = null,
  var userName : String? = null,
  var userStatus: Status = Status.OFFLINE
)

enum class Status{
  OFFLINE, ONLINE, HIDDEN, BLOCKED
}