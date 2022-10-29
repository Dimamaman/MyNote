package com.example.mynote.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.register.Repository
import com.example.mynote.register.core.NetworkResult
import com.example.mynote.register.request.Completed
import com.example.mynote.register.request.UpdataTask
import com.example.mynote.task.data.GetAllTask
import com.example.mynote.task.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelFragment @Inject constructor(val repository: Repository): ViewModel() {
    private val _getAllTask: MutableLiveData<NetworkResult<GetAllTask>> = MutableLiveData()
    val getAllTask: LiveData<NetworkResult<GetAllTask>> = _getAllTask

    private val _deleteTaskById: MutableLiveData<NetworkResult<Task>> = MutableLiveData()
    val deleteTaskById: LiveData<NetworkResult<Task>> = _deleteTaskById

    private val _updateTaskById: MutableLiveData<NetworkResult<UpdataTask>> = MutableLiveData()
    val updataTaskById: LiveData<NetworkResult<UpdataTask>> = _updateTaskById

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
}