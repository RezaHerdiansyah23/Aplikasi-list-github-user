package com.example.aplikasigithubuser.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserResponse(
    @field:SerializedName("items")
    val items: ArrayList<user>
): Parcelable
