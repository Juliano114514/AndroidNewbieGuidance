package com.example.chapter2_core_and_view.lesson2room.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter2_core_and_view.databinding.RoomItemStudentBinding
import com.example.chapter2_core_and_view.lesson2room.data.StudentEntity

class StudentAdapter : ListAdapter<StudentEntity, StudentAdapter.StudentViewHolder>(StudentDiffCallback()) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val binding = RoomItemStudentBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return StudentViewHolder(binding)
  }

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  @SuppressLint("SetTextI18n")
  class StudentViewHolder(private val binding: RoomItemStudentBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(student: StudentEntity){
      binding.tvName.text = "${student.name} (ID: ${student.uid})"
      binding.tvDetail.text = "学号: ${student.studentNumber} | 班级: ${student.className} | 分数: ${student.totalScore}"
    }
  }

  class StudentDiffCallback : DiffUtil.ItemCallback<StudentEntity>(){
    override fun areItemsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean {
      return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean {
      return oldItem == newItem
    }
  }
}