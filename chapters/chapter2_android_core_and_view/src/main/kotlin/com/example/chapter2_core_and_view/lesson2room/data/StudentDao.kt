package com.example.chapter2_core_and_view.lesson2room.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
  @Query("SELECT * FROM students ORDER BY student_number ASC")
  fun getAllStudentsFlow(): Flow<List<StudentEntity>>
  @Query("SELECT * FROM students WHERE name = :keyword OR student_number = :keyword LIMIT 1")
  suspend fun getStudentByKey(keyword: String): StudentEntity?
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertStudent(student: StudentEntity): Long

  @Update
  suspend fun updateStudent(student: StudentEntity)

  @Delete
  suspend fun deleteStudent(student: StudentEntity)

  @Query("DELETE FROM students")
  suspend fun clearAll()

  // 随机抽卡
  @Query("SELECT * FROM students ORDER BY RANDOM() LIMIT 1")
  suspend fun getRandomStudent(): StudentEntity?
}