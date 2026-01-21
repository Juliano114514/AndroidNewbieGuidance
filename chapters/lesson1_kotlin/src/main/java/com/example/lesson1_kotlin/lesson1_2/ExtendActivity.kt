package com.example.lesson1_kotlin.lesson1_2

/**
 * @Author: JULIANO
 * @CreateDate: 2026/1/22 16:34
 * @Description: 拓展函数相关内容
 */
class ExtendActivity {



}

data class UserInfo(
  var userId : String? = null,
  var userName : String? = null,
  var userStatus: Status = Status.OFFLINE
)

enum class Status{
  OFFLINE, ONLINE, HIDDEN, BLOCKED
}