package com.example.chapter2_core_and_view.lesson2room

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.chapter2_core_and_view.lesson2room.data.StudentEntity
import com.example.chapter2_core_and_view.lesson2room.domain.StudentRepository
import com.example.foundation.base.BaseActivity
import com.example.foundation.utils.logger.Logger
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class Lesson2RoomActivity : BaseActivity() {

  // 通过 Koin 注入 Repository
  private val studentRepository: StudentRepository by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // 此处应设置你的 ViewBinding，如：setContentView(binding.root)

    observeDatabase()

    // 模拟交互行为：实际开发中可绑定到 Button 上
    // mockInsertData()
  }

  private fun observeDatabase() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        studentRepository.observeAllStudents().collect { students ->
          // 这里可以直接将数据提交给 RecyclerView 的 Adapter
          Logger.d("Lesson2Room", "当前数据库总人数: ${students.size}")
          students.forEach {
            Logger.d("Lesson2Room", " -> 学号: ${it.studentNumber}, 姓名: ${it.name}, 分数: ${it.totalScore}")
          }
        }
      }
    }
  }

  /**
   * 测试用例：模拟新增数据
   */
  private fun mockInsertData() {
    lifecycleScope.launch {
      val newStudent = StudentEntity(
        studentNumber = "202601001",
        name = "张三",
        className = "研一材料1班",
        gender = "男",
        totalScore = 95.5
      )
      studentRepository.addOrUpdateStudent(newStudent)
      Logger.d("Lesson2Room", "模拟数据插入完成")
    }
  }
}