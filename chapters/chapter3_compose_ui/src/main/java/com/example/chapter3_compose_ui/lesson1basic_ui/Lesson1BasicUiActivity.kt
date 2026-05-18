package com.example.chapter3_compose_ui.lesson1basic_ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chapter3_compose_ui.lesson1basic_ui.ComposeUISeriesHelper.ButtonSet
import com.example.chapter3_compose_ui.lesson1basic_ui.ComposeUISeriesHelper.CheckboxSet
import com.example.chapter3_compose_ui.ui.theme.AppTheme
import com.example.foundation.R
import com.example.foundation.base.BaseActivity
import com.example.foundation.utils.toast.ToastUtil

// 有空要看一下 material design 的完整设计文档
// https://m3.material.io/components
class Lesson1BasicUiActivity : BaseActivity() {

  private val nameList = listOf("压力马斯内","所打哟","你是一个一个一个")
  private val iconList = listOf(R.drawable.round_success, R.drawable.round_warning, R.drawable.round_error)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      AppTheme {
        // 如果要跨文件调用的话，写在外面并且用等号而非代理
        var name by remember { mutableStateOf("李 田所") }
        var name2 by remember { mutableStateOf("田所 浩二") }
        var name3 by remember { mutableStateOf("野兽先辈") }
        var iconRes by remember { mutableIntStateOf(R.mipmap.ic_launcher_round) }
        Column(
          modifier = Modifier
            .safeDrawingPadding()
            .background(MaterialTheme.colorScheme.background)
        ) {
          BasicUiHeader(
            name = name,
            name2 = name2,
            name3 = name3,
            iconRes = iconRes,
          )
          ButtonContent(
            nameList = nameList,
            iconList = iconList,
          )
          SampleLazyRow()
          ButtonSet()
          CheckboxSet()
        }
      }
    }
  }
}

// 面向切面编程 AOP
// 自定义composable
// 在编译期间，会给@Composable修饰的方法添加一些Composer属性
@Composable
fun ButtonContent(
  nameList: List<String> = emptyList(),
  iconList: List<Int> = emptyList(),
) {
  var nameIdx by remember { mutableIntStateOf(0) }
  var iconIdx by remember { mutableIntStateOf(0) }

  Button(
    onClick = {
      ToastUtil.showSuccess("压力马斯内")
      nameIdx = (nameIdx + 1) % nameList.size
      iconIdx = (iconIdx + 1) % iconList.size
    }
  ) {
    Row {
      Icon(
        painter = painterResource(iconList[iconIdx]),
        contentDescription = nameList[nameIdx]
      )
      Text(nameList[nameIdx])
    }
  }
}

@Composable
private fun BasicUiHeader(
  name: String,
  name2: String,
  name3: String,
  iconRes: Int,
) {
  Column {
    Text(
      text = name,
      // 通用的属性用modifier，专用的属性用函数参数
      fontSize = 28.sp,
      fontWeight = FontWeight.Black,
      color = MaterialTheme.colorScheme.onBackground
    )
    // 普通图片用image
    Image(
      painter = painterResource(iconRes),
      contentDescription = name2,
    )
    // 头像和图标用icon（可以实现染色操作）
    Icon(
      painter = painterResource(iconRes),
      contentDescription = name3,
    )
  }
}

@Composable
private fun SampleLazyRow() {
  // 当成rcv使用
  LazyRow(
    modifier = Modifier
      .background(MaterialTheme.colorScheme.primary)
      .fillMaxWidth()
      .wrapContentHeight()
  ) {
    items(20) {
      Text(
        text = "何意味",
        modifier = Modifier.padding(8.dp),
        color = MaterialTheme.colorScheme.onPrimary
      )
    }
    item {
      Text(
        text = "何意味",
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
        fontSize = 28.sp,
        color = MaterialTheme.colorScheme.onPrimaryContainer
      )
    }
  }
}