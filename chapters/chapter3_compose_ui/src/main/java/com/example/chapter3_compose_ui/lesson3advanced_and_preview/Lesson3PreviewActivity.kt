package com.example.chapter3_compose_ui.lesson3advanced_and_preview

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.example.chapter3_compose_ui.ui.theme.AppTheme
import com.example.foundation.R
import com.example.foundation.base.BaseActivity
import com.example.foundation.utils.toast.ToastUtil

class Lesson3PreviewActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ToastUtil.showWarn("请点开源文件查看注解地狱😋")
  }
}

@Composable
fun PersonCard(
  avatar: Painter,
  name: String,
  tittle: String,
  email: String,
){
  AppTheme {
    Card{
      Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Image(
          painter = avatar,
          contentDescription = "Avatar",
          modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
            .padding(2.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(name, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(8.dp))
        Text(
          text = tittle,
          style = MaterialTheme.typography.bodyLarge,
          color = CardDefaults.cardColors().contentColor.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(4.dp))
        Text(
          text = email,
          style = MaterialTheme.typography.bodyMedium,
          color = CardDefaults.cardColors().contentColor.copy(alpha = 0.7f)
        )
      }
    }
  }
}





@DarknessPreview
@FontSizePreview
@SizePreview
@DevicePreview
@BackgroundPreview
@ApiPreview
@Composable
private fun PersonCardPreview(){
  PersonCard(
    avatar = painterResource(R.mipmap.ic_launcher_round),
    name = "田所 浩二",
    tittle = "你是一个一个一个一个啊啊啊",
    email = "114514@yasyu.com "
  )
}

// 注解地狱了orz
@Preview(group="Darkness", name = "Light")
@Preview(group="Darkness", name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class DarknessPreview

@Preview(group="Font Scale", name = "100%", fontScale = 1.0f)
@Preview(group="Font Scale", name = "150%", fontScale = 1.5f)
@Preview(group="Font Scale", name = "200%", fontScale = 2f)
annotation class FontSizePreview

@Preview(group = "size", name = "unmodified")
@Preview(group = "size", name = "modified", widthDp = 150, heightDp = 200)
annotation class SizePreview

@Preview(group = "Devices", name = "Default", showSystemUi = true, device = "spec:width=411dp,height=891dp,dpi=420")
@Preview(group = "Devices", name = "tall", showSystemUi = true, device = "spec:width=411dp,height=891dp,dpi=420,cutout=tall")
@Preview(group = "Devices", name = "punch_hole", showSystemUi = true, device = "spec:width=411dp,height=891dp,dpi=420,cutout=punch_hole")
annotation class DevicePreview

@Preview(group = "Background", name = "ARGB", showBackground = true, backgroundColor = 0xFFFF0000)
@Preview(group = "Background", name = "Wallpaper", wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE)
annotation class BackgroundPreview


@Preview(group = "API", name = "API35", apiLevel = 35)
@Preview(group = "API", name = "API36", apiLevel = 36)
annotation class ApiPreview