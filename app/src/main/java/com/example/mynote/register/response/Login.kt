package com.example.mynote.register.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Login(
    val user: User,
    val token: String
): Parcelable
