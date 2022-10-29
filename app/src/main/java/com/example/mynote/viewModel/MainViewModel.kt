package com.example.mynote.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.register.Repository
import com.example.mynote.register.core.Constants
import com.example.mynote.register.core.NetworkResult
import com.example.mynote.register.request.Completed
import com.example.mynote.register.request.Description
import com.example.mynote.register.request.Register
import com.example.mynote.register.request.UpdataTask
import com.example.mynote.register.response.Login
import com.example.mynote.task.data.GetAllTask
import com.example.mynote.task.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository): ViewModel() {

    private val _registerUser: MutableLiveData<NetworkResult<Login>> = MutableLiveData()
    val registerUser: LiveData<NetworkResult<Login>> = _registerUser

    private val _addTask: MutableLiveData<NetworkResult<Task>> = MutableLiveData()
    val addTask: LiveData<NetworkResult<Task>> = _addTask

    private val _getAllTask: MutableLiveData<NetworkResult<GetAllTask>> = MutableLiveData()
    val getAllTask: LiveData<NetworkResult<GetAllTask>> = _getAllTask

    private val _updateTaskById: MutableLiveData<NetworkResult<UpdataTask>> = MutableLiveData()
    val updataTaskById: LiveData<NetworkResult<UpdataTask>> = _updateTaskById

    private val _deleteTaskById: MutableLiveData<NetworkResult<Task>> = MutableLiveData()
    val deleteTaskById: LiveData<NetworkResult<Task>> = _deleteTaskById


    fun registerUser(user: Register) = viewModelScope.launch {
        repository.registerUser(user).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _registerUser.value = NetworkResult.Succes(it)
                    }
                }else {
                    _registerUser.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _registerUser.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }

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

    fun getAllTask(token: String) = viewModelScope.launch {
        repository.getAllTask(token).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _getAllTask.value = NetworkResult.Succes(it)
                    }
                }else {
                    _getAllTask.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _getAllTask.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }

    fun updateTaskById(id: String, token: String, completed: Completed) = viewModelScope.launch {
        repository.updateTaskById(id, token, completed).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _updateTaskById.value = NetworkResult.Succes(it)
                    }
                }else {
                    _updateTaskById.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _updateTaskById.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }

    fun deleteTaskById(id: String, token: String) = viewModelScope.launch {
        repository.deleteTaskById(id, token).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _deleteTaskById.value = NetworkResult.Succes(it)
                    }
                }else {
                    _deleteTaskById.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _deleteTaskById.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }
}