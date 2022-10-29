package com.example.mynote.task.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    var completed: Boolean,
    @SerializedName("_id") val id: String,
    val description: String,
    val owner: String,
    val createdAt: String,
    val updatedAt: String
): Parcelable
