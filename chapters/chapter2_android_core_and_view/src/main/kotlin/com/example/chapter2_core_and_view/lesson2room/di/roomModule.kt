package com.example.chapter2_core_and_view.lesson2room.di

import androidx.room.Room
import com.example.chapter2_core_and_view.lesson2room.data.StudentDatabase
import com.example.chapter2_core_and_view.lesson2room.domain.StudentRepository
import com.example.chapter2_core_and_view.lesson2room.viewmodel.Lesson2RoomViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val roomModule = module {
  single {
    Room.databaseBuilder(
      androidApplication(),
      StudentDatabase::class.java,
      "student_database"
    )
      .fallbackToDestructiveMigration()
      .build()
  }
  single { get<StudentDatabase>().studentDao() }
  single { StudentRepository(get()) }
  viewModel { Lesson2RoomViewModel(get()) }
}