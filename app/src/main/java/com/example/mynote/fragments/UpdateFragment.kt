package com.example.mynote.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynote.R
import com.example.mynote.databinding.FragmentUpdateBinding
import com.example.mynote.register.core.Constants
import com.example.mynote.register.request.Completed
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateFragment : Fragment(R.layout.fragment_update) {
    private lateinit var binding: FragmentUpdateBinding
    private val updateViewModelFragment: UpdateViewModelFragment by viewModel()
    val args: UpdateFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdateBinding.bind(view)

        binding.apply {
            val update = args.task.description
            Log.d("TTT","----> $update")
            etUpdate.setText(update)

            btnUpdate.setOnClickListener {
                updateViewModelFragment.updateTaskById("${args.task.id}","${Constants.TOKEN}", Completed(true))
                findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            }
        }
    }
}