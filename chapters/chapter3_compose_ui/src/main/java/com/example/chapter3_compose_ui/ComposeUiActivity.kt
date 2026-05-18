package com.example.chapter3_compose_ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chapter3_compose_ui.ui.theme.AppTheme
import com.example.foundation.R
import com.example.foundation.base.BaseActivity
import com.example.kn_shared.bridge.Action
import com.example.kn_shared.bridge.ChapterBridge
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities
import com.example.kn_shared.utils.chapterNavi.ChapterNaviEntities.Chapter3ComposeGuide
import org.koin.android.ext.android.inject

data class ComposeChapterItem(
  val name: String,
  val iconRes: Int = R.mipmap.ic_launcher_round,
  val target: Chapter3ComposeGuide
)

class ComposeUiActivity : BaseActivity() {
  private val chapterBridge: ChapterBridge by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      AppTheme {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          ChapterHeader(chapterBridge)
          Chapter3LessonList(
            onItemClick = { target ->
              chapterBridge.handleAction(Action.ChapterJumpAction(target))
            }
          )
        }
      }
    }
  }

  override fun onHandleNavigation(entity: ChapterNaviEntities): Boolean {
    return if (entity is Chapter3ComposeGuide) {
      ComposeLessonNaviProxy.jumpTo(this, entity)
      true
    } else {
      false
    }
  }
}

@Composable
private fun Chapter3LessonList(onItemClick: (Chapter3ComposeGuide) -> Unit) {
  val itemsList = remember {
    listOf(
      ComposeChapterItem(
        name = "Lesson1\n基础UI",
        target = Chapter3ComposeGuide.Lesson1BasicUi
      ),
      ComposeChapterItem(
        name = "Lesson2\nModifier",
        target = Chapter3ComposeGuide.Lesson2Modifier
      ),
      ComposeChapterItem(
        name = "Lesson3\n@Preview相关",
        target = Chapter3ComposeGuide.Lesson3AdvancedAndPreView
      )
    )
  }
  LazyVerticalGrid(
    columns = GridCells.Fixed(3),
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(bottom = 16.dp)
  ) {
    items(itemsList) { item ->
      ChapterCardItem(
        item = item,
        onClick = { onItemClick(item.target) }
      )
    }
  }
}

@Composable
private fun ChapterHeader(chapterBridge: ChapterBridge) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 16.dp), // 统一给整个行加底部间距
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Box(modifier = Modifier.weight(1f))

    Text(
      text = "Chapter3 Compose相关",
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onBackground,
    )
    IconButton(
      onClick = {
        val target = Action.ChapterJumpAction(Chapter3ComposeGuide.CustomizeTheme)
        chapterBridge.handleAction(target)
      },
      modifier = Modifier.weight(1f),
    ) {
      Icon(
        painter = painterResource(R.drawable.theme_setting),
        contentDescription = "主题设置",
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.size(24.dp)
      )
    }
  }
}

@Composable
private fun ChapterCardItem(
  item: ComposeChapterItem,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .padding(10.dp)
      .height(120.dp)
      .clickable(onClick = onClick),
    shape = RoundedCornerShape(8.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Image(
        painter = painterResource(id = item.iconRes),
        contentDescription = item.name,
        modifier = Modifier.size(48.dp)
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = item.name,
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface
      )
    }
  }
}