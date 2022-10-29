package com.example.mynote.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home),TaskAdapter.onItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModelFragment by viewModels()
    private val taskAdapter = TaskAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        initRec()

//        taskAdapter.isChecked { data ->
//            viewModel.updateTaskById("${data.id}","${Constants.TOKEN}", Completed(true))
//            Log.d("QQQ","----> ${data.id}")
//            Log.d("PPP","---> ${data.completed}")
//
//            viewModel.updataTaskById.observe(requireActivity()) {
//                when(it) {
//                    is NetworkResult.Succes -> {
//                        Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
//
//                    }
//                    is NetworkResult.Error -> {
//                        Toast.makeText(requireContext(), "Error Update Task", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }

        viewModel.getAllTask.observe(requireActivity()) {
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

        viewModel.getAllTask("${Constants.TOKEN}")

        binding.apply {
            addFloatingBtn.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)
            }
        }

        taskAdapter.removeItemClick { data, position ->
            viewModel.deleteTaskById("${data.id}","${Constants.TOKEN}")
            viewModel.deleteTaskById.observe(requireActivity()) {
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
            viewModel.updateTaskById(it.id, "Bearer $TOKEN", Completed(true))

            viewModel.updataTaskById.observe(requireActivity()) {
                when(it) {
                    is NetworkResult.Succes -> {
                        Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                        viewModel.getAllTask("Bearer $TOKEN")
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(requireContext(), "Error Update Task", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initRec() {
        binding.apply {
            recyclerview.adapter = taskAdapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            recyclerview.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        }
    }

    override fun onItemClick(task: Data) {
//        val bundle = bundleOf("task" to task)
        val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(task)
        findNavController().navigate(action)
    }

    override fun onCheckBoxClick(task: Data, isChecked: Completed) {
//        viewModel.updateTaskById(task.id,"${Constants.TOKEN}", Completed(true))
    }
}