package com.example.mynote.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.register.Repository
import com.example.mynote.register.core.NetworkResult
import com.example.mynote.register.request.Completed
import com.example.mynote.register.request.UpdataTask
import kotlinx.coroutines.launch


class UpdateViewModelFragment (val repository: Repository): ViewModel() {

    private val _updateTaskById: MutableLiveData<NetworkResult<UpdataTask>> = MutableLiveData()
    val updataTaskById: LiveData<NetworkResult<UpdataTask>> = _updateTaskById

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