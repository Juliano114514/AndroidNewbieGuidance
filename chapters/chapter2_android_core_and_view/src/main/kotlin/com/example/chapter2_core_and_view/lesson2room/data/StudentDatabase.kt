package com.example.chapter2_core_and_view.lesson2room.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [StudentEntity::class],
  version = 1,
  exportSchema = false
)
abstract class StudentDatabase : RoomDatabase() {
  abstract fun studentDao(): StudentDao
}