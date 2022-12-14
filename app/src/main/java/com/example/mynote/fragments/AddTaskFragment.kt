package com.example.mynote.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mynote.R
import com.example.mynote.databinding.FragmentAddTaskBinding
import com.example.mynote.register.core.Constants
import com.example.mynote.register.core.NetworkResult
import com.example.mynote.register.request.Description
import com.example.mynote.task.data.Data
import com.example.mynote.task.data.Task
import com.example.mynote.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : Fragment(R.layout.fragment_add_task) {
    private lateinit var binding: FragmentAddTaskBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTaskBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("shared",Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        binding.apply {
            addTask.setOnClickListener {
                mainViewModel.addTask.observe(requireActivity()) {
                    when (it) {
                        is NetworkResult.Succes -> {
                            Toast.makeText(requireContext(), "Task Saved", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(R.id.action_addTaskFragment_to_homeFragment)
                        }
                        is NetworkResult.Error -> {
                            Toast.makeText(requireContext(), "No saved", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                Log.d("SSS","-----> ${token.toString()}")
                mainViewModel.addTask("Bearer $token", Description(task1.text.toString()))
            }
        }
    }
}