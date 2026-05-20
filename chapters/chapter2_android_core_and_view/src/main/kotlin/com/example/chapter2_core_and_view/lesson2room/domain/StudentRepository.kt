package com.example.chapter2_core_and_view.lesson2room.domain

import com.example.chapter2_core_and_view.lesson2room.data.StudentDao
import com.example.chapter2_core_and_view.lesson2room.data.StudentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class StudentRepository(private val studentDao: StudentDao) {

  fun observeAllStudents(): Flow<List<StudentEntity>> {
    return studentDao.getAllStudentsFlow()
  }

  suspend fun addOrUpdateStudent(student: StudentEntity) = withContext(Dispatchers.IO) {
    if (student.uid == 0L) { // 缺省值说明没有入过库
      studentDao.insertStudent(student)
    } else {
      studentDao.updateStudent(student)
    }
  }

  suspend fun deleteStudent(student: StudentEntity) = withContext(Dispatchers.IO) {
    studentDao.deleteStudent(student)
  }

  suspend fun getStudentByKey(key: String): StudentEntity? = withContext(Dispatchers.IO){
    studentDao.getStudentByKey(key)
  }

  suspend fun getRandomStudent(): StudentEntity? = withContext(Dispatchers.IO) {
    studentDao.getRandomStudent()
  }
  suspend fun clearAll() = withContext(Dispatchers.IO){
    studentDao.clearAll()
  }

  suspend fun importFromCsv(filePath: String): Result<Int> = withContext(Dispatchers.IO) {
    runCatching {
      // TODO: 解析 CSV 文件，构建 List<StudentEntity> 并通过 studentDao 批量插入
      0
    }
  }

}