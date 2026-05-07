package com.example.chapter2_core_and_view.lesson1ksp.entities

import com.example.lib_ksp_annotation.MyColumn
import com.example.lib_ksp_annotation.MyEntity

@MyEntity(tableName = "t_student")
data class Student(
  @MyColumn(columnName = "stu_id", type = "INTEGER PRIMARY KEY")
  val id: Int,

  @MyColumn(columnName = "stu_name", type = "VARCHAR(50)")
  val name: String,

  // 没有注解的字段，你的 Processor 应该忽略它
  val age: Int
)