package com.example.mynote.register.core

sealed class NetworkResult<T>(val data: T? = null,val message: String? = null) {
    class Succes<T>(data: T): NetworkResult<T>(data)
    class Error<T>(message: String,data: T? = null,): NetworkResult<T>(data, message)
}
