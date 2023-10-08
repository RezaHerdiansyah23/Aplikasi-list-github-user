package com.example.aplikasigithubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface FavoriteDao {

    @Insert
    fun addToFavorite(favoriteUser: FavoriteEntry)

    @Query("SELECT * FROM favorite")
    fun getFavoriteUser(): LiveData<List<FavoriteEntry>>

    @Query("SELECT count(*) FROM favorite WHERE favorite.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    fun removeFromFavorite(id: Int): Int
}