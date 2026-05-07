package com.example.chapter2_core_and_view.lesson1ksp

import android.view.View
import com.example.chapter2_core_and_view.databinding.Lesson1KspLayoutBinding
import com.example.chapter2_core_and_view.lesson1ksp.entities.Student
import com.example.chapter2_core_and_view.lesson1ksp.entities.Student_TableInfo
import com.example.chapter2_core_and_view.lesson1ksp.entities.Teacher_TableInfo
import com.example.foundation.base.BaseActivity
import com.example.foundation.base.presenter.BasePresenter
import com.example.foundation.utils.logger.Logger

class Lesson1KspPresenter : BasePresenter() {
  private var binding: Lesson1KspLayoutBinding? = null

  override fun onBind(activity: BaseActivity, rootView: View) {
    super.onBind(activity, rootView)
    binding = Lesson1KspLayoutBinding.bind(rootView)
    initListeners()
  }

  private fun initListeners(){
    binding?.btnStudentInfo?.setOnClickListener {
      Student_TableInfo.printInfo()

      val result = """
                === KSP 动态生成类调用成功 ===
                目标类: Student_TableInfo
                提取表名: ${Student_TableInfo.TABLE_NAME}
                
                ※ 具体的字段信息（MyColumn）已通过 println 打印，请打开 Logcat 并过滤 "System.out" 即可查看详细的字段与类型映射。
            """.trimIndent()

      binding?.tvKspResult?.text = result
      Logger.d("Lesson1Ksp", "Student_TableInfo 执行完毕")
    }

    binding?.btnTeacherInfo?.setOnClickListener {
      Teacher_TableInfo.printInfo()

      val result = """
                === KSP 动态生成类调用成功 ===
                目标类: Teacher_TableInfo
                提取表名: ${Teacher_TableInfo.TABLE_NAME}
                
                ※ 具体的字段信息已打印到系统控制台 (System.out)。
            """.trimIndent()

      binding?.tvKspResult?.text = result
      Logger.d("Lesson1Ksp", "Teacher_TableInfo 执行完毕")
    }
  }

  override fun onUnbindView() {
    super.onUnbindView()
    binding = null
  }
}