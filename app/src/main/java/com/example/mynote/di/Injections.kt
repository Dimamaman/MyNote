package com.example.mynote.di

import com.example.mynote.RegisterViewModelActivity
import com.example.mynote.adapter.TaskAdapter
import com.example.mynote.fragments.AddTaskViewModelFragment
import com.example.mynote.fragments.HomeViewModelFragment
import com.example.mynote.fragments.UpdateViewModelFragment
import com.example.mynote.register.Repository
import com.example.mynote.register.core.Constants
import com.example.mynote.register.retrofit.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    single<Repository> {
        Repository(get())
    }
}

val viewModelModule = module {
    viewModel { AddTaskViewModelFragment(get()) }
    viewModel { HomeViewModelFragment(get()) }
    viewModel { UpdateViewModelFragment(get()) }
    viewModel {RegisterViewModelActivity(get())}
}

val adapterModule = module {
    single { TaskAdapter() }
}