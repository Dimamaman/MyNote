package com.example.mynote.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynote.R
import com.example.mynote.databinding.FragmentAddTaskBinding
import com.example.mynote.register.core.NetworkResult
import com.example.mynote.register.request.Description
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {
    private lateinit var binding: FragmentAddTaskBinding
    private val addTaskViewModelFragment: AddTaskViewModelFragment by viewModel()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTaskBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("shared",Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        binding.apply {
            addTask.setOnClickListener {
                addTaskViewModelFragment.addTask.observe(requireActivity()) {
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
                addTaskViewModelFragment.addTask("Bearer $token", Description(task1.text.toString()))
            }
        }
    }
}