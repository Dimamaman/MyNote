package com.example.mynote.register.request

data class Register(
    val name: String,
    val email: String,
    val password: String,
    val age: Int
)
