package com.example.chapter3_compose_ui.lesson2modifier

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.chapter3_compose_ui.ui.theme.AppTheme
import com.example.foundation.R
import com.example.foundation.base.BaseActivity
import com.example.foundation.utils.toast.ToastUtil

class Lesson2ModifierActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      AppTheme {
        Column(
          verticalArrangement = Arrangement.Top,
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .safeDrawingPadding()
            .background(MaterialTheme.colorScheme.background)
        ) {
          Row {
            YasyuSenpai()
            ButtonSet()
          }
          TargetView()
        }
      }
    }
  }
}

// 通用的功能由Modifier承载
// 专有的属性用函数参数实现
@Composable
private fun YasyuSenpai() {
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    // FIFO，modifier链式调用的顺序决定背景和边距的呈现效果
    modifier = Modifier
      .padding(16.dp)
      .background(
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(8.dp) // 设置背景为圆角矩形
      )
      .padding(8.dp)
      .background(MaterialTheme.colorScheme.primaryContainer)
      .width(90.dp)  // 指定宽高
      .height(120.dp) // 链式调用，会最终设高为75
  ) {
    var name by remember { mutableStateOf("我是谁") }
    Image(
      painter = painterResource(R.mipmap.ic_launcher),
      contentDescription = "",
      modifier = Modifier
        .clip(CircleShape) // 裁切成圆形
        .size(75.dp)  // match_parent
        // clickable 会根据当前的面积来设定热区，设定之后再调整padding，响应区不会随之变化
        .clickable {   // 点击事件这一块
          name = if (name == "野兽先辈") "田所 浩二" else "野兽先辈"
        }
    )
    Text(
      text = name,
      fontSize = 15.sp,
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}

/*
 * @Modifier.clickable(onClick) 实际上是一个赋值操作，类似于 setOnEventListener
 * 也就是 只有最后一个 onClick 会保留
 *
 * modifier 是链式调用的
 * 在官方的 @Composable 子类中
 * modifier会在外面执行完我们的链式调用
 * 之后才会传给子类
 *
 * 而Button的Onclick在最后一层子类才会传递给 modifier.clickable
 * 因此一定会覆写掉我们传入的clickable
 *
 */

@Composable
private fun ButtonSet() {
  var name by remember { mutableStateOf("我是谁") }
  val fun1 = {
    name = "野兽先辈"
    ToastUtil.showSuccess("onClick发力了")
  }
  val fun2 = {
    name = "田所浩二"
    ToastUtil.showWarn("clickable发力了")
  }
  val emptyFun = {}

  var mFun1 by remember { mutableStateOf<() -> Unit>(fun1) }


  Column(
    modifier = Modifier
      .padding(24.dp)
  ) {
    Text(
      text = name,
      fontSize = 15.sp,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Button(
      onClick = mFun1,
      modifier = Modifier
        .clickable { fun2 },
    ) {
      Text("点击测试")
    }

    Button(
      onClick = {
        if (mFun1 != emptyFun) {
          ToastUtil.showWarn("onClick 已清空")
          mFun1 = emptyFun
        } else {
          ToastUtil.showSuccess("onClick 已恢复")
          mFun1 = fun1
        }
      }
    ) {
      Text("点击禁用onClick")
    }
  }
}

// 通过padding设定不同的热区
@Composable
private fun TargetView() {

  val colors = listOf(
    Color(204, 153, 153), // 红
    Color(219, 173, 141), // 橙
    Color(222, 201, 153), // 黄
    Color(173, 191, 153), // 绿
    Color(153, 191, 189), // 青
    Color(142, 163, 196), // 蓝
    Color(173, 153, 179)  // 紫
  )

  val ringTexts = listOf("红1环", "橙2环", "黄3环", "绿4环", "青5环", "蓝6环", "紫7环")
  val maxSize = 380.dp  // 最外圈直径
  val step = 55.dp      // 每圈减少的宽度

  // 堆叠 7 个同心圆
  Box(
    modifier = Modifier.size(maxSize)
  ) {
    for (i in 0..6) {
      val currentSize = maxSize - i * step
      Box(
        modifier = Modifier
          .align(Alignment.Center) // 调整gravity，优先级比函数参数设置的更高
          .clip(CircleShape)
          .size(currentSize)
          .background(colors[i])
          .clickable {
            ToastUtil.showToast(ringTexts[i])
          }
      )
    }
  }
}