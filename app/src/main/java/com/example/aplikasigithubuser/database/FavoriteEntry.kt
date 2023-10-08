package com.example.aplikasigithubuser.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(
    tableName = "favorite"
)


@Parcelize
data class FavoriteEntry (
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String


    ): Parcelable