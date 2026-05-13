package com.example.chapter3_compose_ui.lesson1basic_ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.foundation.utils.toast.ToastUtil

object ComposeUISeriesHelper {

  private val onBtnClick: (String) -> () -> Unit = { toastMsg ->
    { ToastUtil.showToast(toastMsg) }
  }

  @Composable
  fun ButtonSet(){
    var name by remember { mutableStateOf("我是谁") }
    Row(){
      Button(onBtnClick("我是 Button")) {
        Text(name)
      }

      ElevatedButton(onBtnClick("我是 ElevatedButton")) {
        Text(name)
      }

      OutlinedButton(onBtnClick("我是OutlinedButton")) {
        Text(name)
      }

      TextButton(onBtnClick("我是TextButton")) {
        Text(name)
      }

    }
  }
}