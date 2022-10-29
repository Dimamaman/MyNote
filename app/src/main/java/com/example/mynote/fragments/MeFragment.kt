package com.example.mynote.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mynote.R
import com.example.mynote.databinding.FragmentMeBinding

class MeFragment : Fragment(R.layout.fragment_me) {
    lateinit var binding: FragmentMeBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMeBinding.bind(view)

        sharedPreferences = requireActivity().getSharedPreferences("shared",Context.MODE_PRIVATE)

        binding.apply {
            userName.text = sharedPreferences.getString("name","Error")
            userEmai.text = sharedPreferences.getString("email","Error")
        }
    }
}