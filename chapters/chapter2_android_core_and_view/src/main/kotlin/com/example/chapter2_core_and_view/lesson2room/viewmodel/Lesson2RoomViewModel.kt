package com.example.chapter2_core_and_view.lesson2room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapter2_core_and_view.lesson2room.data.StudentEntity
import com.example.chapter2_core_and_view.lesson2room.domain.StudentRepository
import com.example.chapter2_core_and_view.lesson2room.utils.NameGenerator
import com.example.foundation.utils.toast.ToastUtil
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random.Default.nextFloat

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
      val newStudent = StudentEntity(
        studentNumber = "222026$randomId",
        name = NameGenerator.generateRandomName(),
        className = "沼气科学114514班",
        gender = if(randomId % 2 == 0) "男" else "女",
        totalScore = nextFloat() * 100.toDouble()
      )
      repository.addOrUpdateStudent(newStudent)
    }
  }

  fun insertTarget(name: String) {
    if (name.isBlank()) return
    viewModelScope.launch {
      val student = StudentEntity(
        studentNumber = "Custom-${System.currentTimeMillis() % 10000}",
        name = name,
        className = "速达优1919810班",
        gender = "沃尔玛购物袋",
        totalScore = 11.4514
      )
      repository.addOrUpdateStudent(student)
    }
  }

  fun updateTarget(key: String) {
    if (key.isBlank()) return
    viewModelScope.launch {
      repository.getStudentByKey(key)?.let { target ->
        ToastUtil.showSuccess("同学报下学号，加A分喵😋")
        val updatedStudent = target.copy(totalScore = target.totalScore + 10.0)
        repository.addOrUpdateStudent(updatedStudent)
      }
    }
  }

  fun deleteTarget(key: String) {
    if (key.isBlank()) return
    viewModelScope.launch {
      repository.getStudentByKey(key)?.let { target ->
        repository.deleteStudent(target)
      }
    }
  }

  fun clearAllData() {
    viewModelScope.launch {
      repository.clearAll()
    }
  }
}