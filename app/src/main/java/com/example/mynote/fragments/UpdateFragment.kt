package com.example.mynote.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynote.R
import com.example.mynote.databinding.FragmentUpdateBinding
import com.example.mynote.register.core.Constants
import com.example.mynote.register.request.Completed
import com.example.mynote.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment(R.layout.fragment_update) {
    private lateinit var binding: FragmentUpdateBinding
    private val mainViewModel: MainViewModel by viewModels()
    val args: UpdateFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdateBinding.bind(view)

        binding.apply {
            val update = args.task.description
            Log.d("TTT","----> $update")
            etUpdate.setText(update)

            btnUpdate.setOnClickListener {
                mainViewModel.updateTaskById("${args.task.id}","${Constants.TOKEN}", Completed(true))
                findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            }
        }
    }
}