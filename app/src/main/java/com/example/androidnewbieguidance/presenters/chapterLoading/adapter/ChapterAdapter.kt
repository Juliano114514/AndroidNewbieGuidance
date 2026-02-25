package com.example.androidnewbieguidance.presenters.chapterLoading.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidnewbieguidance.R
import com.example.kn_shared.utils.chapterJump.ChapterJumpEntities

/**
 * @Author: JULIANO
 * @CreateDate: 2026/2/14 16:43
 * @Description:
 */
class ChapterAdapter(
  private val onItemClick: (ChapterJumpEntities) -> Unit
) : RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

  private val items = mutableListOf<ChapterItem>()

  @SuppressLint("NotifyDataSetChanged")
  fun refreshList(list: List<ChapterItem>){
    items.clear()
    items.addAll(list)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_chapter, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = items[position]
    holder.tvName.text = item.name
    holder.ivIcon.setImageResource(item.iconRes)
  }

  override fun getItemCount(): Int = items.size

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val tvName: TextView = itemView.findViewById(R.id.tv_chapter_name)
    val ivIcon: ImageView = itemView.findViewById(R.id.iv_chapter_icon)

    init{
      itemView.setOnClickListener {
        val position = bindingAdapterPosition
        if(position != RecyclerView.NO_POSITION){
          onItemClick(items[position].target)
        }
      }
    }
  }
}

data class ChapterItem(
  val name: String,
  val iconRes: Int = R.mipmap.ic_launcher_round,
  val target: ChapterJumpEntities // 点击后发送的信号
)