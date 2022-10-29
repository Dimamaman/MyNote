package com.example.mynote

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mynote.databinding.ActivityRegisterBinding
import com.example.mynote.register.core.Constants
import com.example.mynote.register.core.NetworkResult
import com.example.mynote.register.request.Register
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val registerViewModelActivity: RegisterViewModelActivity by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE)

        binding.apply {
            registerBtn.setOnClickListener {
                val user = Register(
                    name1.text.toString(),
                    email1.text.toString(),
                    password1.text.toString(),
                    age1.text.toString().toInt()
                )

                registerViewModelActivity.registerUser(user)
                registerViewModelActivity.registerUser.observe(this@RegisterActivity) {
                    when (it) {
                        is NetworkResult.Succes -> {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Successfully Registed",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            sharedPreferences.edit().putString("token", it.data!!.token).apply()
                            Constants.TOKEN = it.data.token
                            sharedPreferences.edit().putString("name",it.data.user.name).apply()
                            sharedPreferences.edit().putString("id",it.data.user.id).apply()
                            sharedPreferences.edit().putString("email",it.data.user.email).apply()
                            startActivity(Intent(this@RegisterActivity,MainActivity::class.java))
                            finish()
                        }
                        is NetworkResult.Error -> {
                            Toast.makeText(this@RegisterActivity, "Error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}