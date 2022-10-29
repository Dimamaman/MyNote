package com.example.mynote.register

import com.example.mynote.register.request.Completed
import com.example.mynote.register.request.Description
import com.example.mynote.register.request.Register
import com.example.mynote.register.retrofit.ApiService
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun registerUser(user: Register) = apiService.registerUser(user)

    suspend fun addTask(token: String, description: Description) =
        apiService.addTask(token, description)

    suspend fun getAllTask(token: String) = apiService.getAllTask(token)

    suspend fun updateTaskById(id: String, token: String,completed: Completed) =
        apiService.updateTaskById(id, token, completed)

    suspend fun deleteTaskById(id: String, token: String) = apiService.deleteTaskById(id, token)
}