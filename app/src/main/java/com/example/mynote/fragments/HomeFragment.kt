package com.example.mynote.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynote.R
import com.example.mynote.adapter.TaskAdapter
import com.example.mynote.databinding.FragmentHomeBinding
import com.example.mynote.register.core.Constants
import com.example.mynote.register.core.Constants.TOKEN
import com.example.mynote.register.core.NetworkResult
import com.example.mynote.register.request.Completed
import com.example.mynote.task.data.Data
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModelFragment: HomeViewModelFragment by viewModel()
    private val taskAdapter: TaskAdapter by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initRec()
        homeViewModelFragment.getAllTask.observe(requireActivity()) {
            when(it) {
                is NetworkResult.Succes -> {
                    taskAdapter.model = it.data?.data ?: mutableListOf()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.d("UUU","---> ${Constants.TOKEN}")

        homeViewModelFragment.getAllTask("${Constants.TOKEN}")

        binding.apply {
            addFloatingBtn.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)
            }
        }

        taskAdapter.removeItemClick { data, position ->
            homeViewModelFragment.deleteTaskById("${data.id}","${Constants.TOKEN}")
            homeViewModelFragment.deleteTaskById.observe(requireActivity()) {
                when(it) {
                    is NetworkResult.Succes -> {
                        Toast.makeText(requireContext(), "Task Deleted", Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            taskAdapter.removeItem(position)
        }

        taskAdapter.setOnCheckboxClickListener {
            homeViewModelFragment.updateTaskById(it.id, "Bearer $TOKEN", Completed(true))

            homeViewModelFragment.updataTaskById.observe(requireActivity()) {
                when(it) {
                    is NetworkResult.Succes -> {
                        Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                        homeViewModelFragment.getAllTask("Bearer $TOKEN")
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(requireContext(), "Error Update Task", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        taskAdapter.onItemClick {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun initRec() {
        binding.apply {
            recyclerview.adapter = taskAdapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            recyclerview.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        }
    }
}