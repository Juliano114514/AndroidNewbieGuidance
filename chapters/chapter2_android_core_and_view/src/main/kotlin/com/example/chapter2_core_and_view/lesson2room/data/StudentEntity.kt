package com.example.chapter2_core_and_view.lesson2room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
  @PrimaryKey(autoGenerate = true)
  val uid: Long = 0L,

  @ColumnInfo(name = "student_number")
  val studentNumber: String,

  @ColumnInfo(name = "name")
  val name: String,

  @ColumnInfo(name = "class_name")
  val className: String,

  @ColumnInfo(name = "gender")
  val gender: String,

  @ColumnInfo(name = "total_score")
  val totalScore: Double
)