package com.example.aplikasigithubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteEntry::class], version = 1)
abstract class FavoriteRoomDatabase: RoomDatabase() {
        abstract fun favDao(): FavoriteDao

    companion object {
        var INSTANCE: FavoriteRoomDatabase? = null

        fun getDatabase(context: Context): FavoriteRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(FavoriteRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteRoomDatabase::class.java, "fav_database").build()
                }
            }
            return INSTANCE
        }
    }

}