package com.example.chapter2_core_and_view.lesson2room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapter2_core_and_view.lesson2room.data.StudentEntity
import com.example.chapter2_core_and_view.lesson2room.domain.StudentRepository
import com.example.chapter2_core_and_view.lesson2room.utils.NameGenerator
import com.example.chapter2_core_and_view.lesson2room.utils.StudentToastHelper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random.Default.nextDouble

class Lesson2RoomViewModel(
  private val repository: StudentRepository
) : ViewModel() {
  val studentListState: StateFlow<List<StudentEntity>> = repository.observeAllStudents()
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = emptyList()
    )

  fun addRandomData(){
    viewModelScope.launch {
      val randomId = (1000..9999).random()
      val randomClass = (1..114).random()
      val newStudent = StudentEntity(
        studentNumber = "222026$randomId",
        name = NameGenerator.generateRandomName(),
        className = "沼气科学${randomClass}班",
        gender = if(randomId % 2 == 0) "男" else "女",
        totalScore = nextDouble() * 100
      )
      repository.addOrUpdateStudent(newStudent)
      StudentToastHelper.showRandomStudentEnrolled(newStudent.name)
    }
  }

  fun insertTarget(name: String) {
    if (name.isBlank()) {
      StudentToastHelper.showInsertNameRequired()
      return
    }
    viewModelScope.launch {
      val student = StudentEntity(
        studentNumber = "插班生-${System.currentTimeMillis() % 10000}",
        name = name,
        className = "速达优1919810班",
        gender = "沃尔玛购物袋",
        totalScore = nextDouble() * 100
      )
      repository.addOrUpdateStudent(student)
      StudentToastHelper.showInsertSuccess(student.name)
    }
  }

  fun updateTarget(key: String) {
    viewModelScope.launch {
      val target = if (key.isBlank()) {
        repository.getRandomStudent()
      } else {
        repository.getStudentByKey(key)
      }

      target?.let {
        var score = nextDouble() * 10
        val isDeduction = nextDouble() < 0.25
        if (isDeduction) score *= -1
        StudentToastHelper.showUpdateScoreSuccess(it.name, isDeduction)
        val updatedStudent = it.copy(totalScore = it.totalScore + score)
        repository.addOrUpdateStudent(updatedStudent)
      } ?: run {
        StudentToastHelper.showLookupFailed(key.isBlank())
      }
    }
  }

  fun deleteTarget(key: String) {
    viewModelScope.launch {
      val target = if (key.isBlank()) {
        repository.getRandomStudent()
      } else {
        repository.getStudentByKey(key)
      }

      target?.let {
        StudentToastHelper.showDeleteSuccess(it.name)
        repository.deleteStudent(it)
      } ?: run {
        StudentToastHelper.showLookupFailed(key.isBlank())
      }
    }
  }

  fun clearAllData() {
    viewModelScope.launch {
      repository.clearAll()
      StudentToastHelper.showClearAllSuccess()
    }
  }
}