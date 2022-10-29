package com.example.mynote.register.retrofit

import com.example.mynote.register.request.Completed
import com.example.mynote.register.request.Description
import com.example.mynote.register.request.Register
import com.example.mynote.register.request.UpdataTask
import com.example.mynote.register.response.Login
import com.example.mynote.task.data.Data
import com.example.mynote.task.data.GetAllTask
import com.example.mynote.task.data.Task
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("user/register")
    suspend fun registerUser(
        @Body user: Register
    ): Response<Login>

    @POST("task")
    suspend fun addTask(
        @Header("Authorization") token: String,
        @Body description: Description
    ): Response<Task>

    @GET("task")
    suspend fun getAllTask(
        @Header("Authorization") token: String,
    ): Response<GetAllTask>

    @PUT("task/{id}")
    suspend fun updateTaskById(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body completed: Completed
    ): Response<UpdataTask>

    @DELETE("task/{id}")
    suspend fun deleteTaskById(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<Task>

    @GET("task/{id}")
    suspend fun getTaskById (
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<Task>

}