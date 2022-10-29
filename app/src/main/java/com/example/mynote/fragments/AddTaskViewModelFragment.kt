package com.example.mynote.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.register.Repository
import com.example.mynote.register.core.Constants
import com.example.mynote.register.core.NetworkResult
import com.example.mynote.register.request.Description
import com.example.mynote.task.data.Task
import kotlinx.coroutines.launch


class AddTaskViewModelFragment(val repository: Repository): ViewModel() {
    private val _addTask: MutableLiveData<NetworkResult<Task>> = MutableLiveData()
    val addTask: LiveData<NetworkResult<Task>> = _addTask

    fun addTask(token: String,description: Description) = viewModelScope.launch {
        Log.d("Token","----> ${Constants.TOKEN}")
        repository.addTask(token,description).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _addTask.value = NetworkResult.Succes(it)
                    }
                }else {
                    Log.d("Quwirdaq","---> ${response.message()}")
                    _addTask.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _addTask.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }
}