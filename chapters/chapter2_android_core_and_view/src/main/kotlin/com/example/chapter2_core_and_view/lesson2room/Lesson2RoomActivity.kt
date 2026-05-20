package com.example.chapter2_core_and_view.lesson2room

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter2_core_and_view.databinding.Lesson2RoomLayoutBinding
import com.example.chapter2_core_and_view.lesson2room.adapter.StudentAdapter
import com.example.chapter2_core_and_view.lesson2room.viewmodel.Lesson2RoomViewModel
import com.example.foundation.base.BaseActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class Lesson2RoomActivity : BaseActivity() {
  private val viewModel: Lesson2RoomViewModel by viewModel()
  private lateinit var binding: Lesson2RoomLayoutBinding
  private val studentAdapter by lazy { StudentAdapter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = Lesson2RoomLayoutBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupViews()
    observeViewModel()
  }

  private fun setupViews(){
    setupBtn()
    binding.rvDataDisplay.apply {
      layoutManager = LinearLayoutManager(this@Lesson2RoomActivity)
      adapter = studentAdapter
    }
  }

  private fun setupBtn(){
    val btnSet = binding.llButtonRow

    btnSet.btnAdd.setOnClickListener {
      viewModel.addRandomData()
    }

    btnSet.btnInsert.setOnClickListener {
      val input = binding.etTargetInput.text.toString()
      viewModel.insertTarget(input)
      binding.etTargetInput.text.clear()
    }

    btnSet.btnUpdate.setOnClickListener {
      val input = binding.etTargetInput.text.toString()
      viewModel.updateTarget(input)
    }

    btnSet.btnDelete.setOnClickListener {
      val input = binding.etTargetInput.text.toString()
      viewModel.deleteTarget(input)
    }

    btnSet.btnClear.setOnClickListener {
      viewModel.clearAllData()
    }
  }

  private fun observeViewModel() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.studentListState.collect { students ->
          studentAdapter.submitList(students)
        }
      }
    }
  }
}