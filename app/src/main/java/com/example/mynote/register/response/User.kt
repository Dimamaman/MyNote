package com.example.mynote.register.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    val age: Int,
    @SerializedName("_id") val id: String,
    val name: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String
): Parcelable