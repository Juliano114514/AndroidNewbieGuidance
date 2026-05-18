package com.example.chapter3_compose_ui.lesson1basic_ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.chapter3_compose_ui.lesson1basic_ui.ComposeUISeriesHelper.ButtonSet
import com.example.chapter3_compose_ui.lesson1basic_ui.ComposeUISeriesHelper.CheckboxSet
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


  @Composable
  fun CheckboxSet(){
    Row(){
      var isChecked by remember { mutableStateOf(true) }
      var isIntercepted by remember { mutableStateOf(false) }
      var name by remember { mutableStateOf("点击拦截") }
      Checkbox(
        checked = isChecked,
        // 选中状态需要手动更新，以保证单一信息源原则
        onCheckedChange = { newState ->
          if(isIntercepted){
            ToastUtil.showFail("切换状态失败，请检查条件")
          } else{
            val state = if(newState) "勾选" else "取消"
            ToastUtil.showSuccess("${state}成功")
            isChecked = newState
          }
        }
      )

      Button(
        onClick = {
          isIntercepted = !isIntercepted
          name = if(isIntercepted) "点击恢复" else "点击拦截"
        }
      ){
        Text(name)
      }
    }
  }
}

@Preview
@Composable
private fun ButtonPreview(){
  ButtonSet()
}


@Preview
@Composable
private fun CheckboxPreview(){
  CheckboxSet()
}