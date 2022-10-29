package com.example.mynote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.register.Repository
import com.example.mynote.register.core.NetworkResult
import com.example.mynote.register.request.Register
import com.example.mynote.register.response.Login
import kotlinx.coroutines.launch

class RegisterViewModelActivity(val repository: Repository): ViewModel() {
    private val _registerUser: MutableLiveData<NetworkResult<Login>> = MutableLiveData()
    val registerUser: LiveData<NetworkResult<Login>> = _registerUser

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
}