package com.example.aplikasigithubuser.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.aplikasigithubuser.database.FavoriteDao
import com.example.aplikasigithubuser.database.FavoriteEntry
import com.example.aplikasigithubuser.database.FavoriteRoomDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {


    private var favorDao: FavoriteDao?
    private var userDb: FavoriteRoomDatabase?
    init {
        userDb = FavoriteRoomDatabase.getDatabase(application)
        favorDao = userDb?.favDao()

    }

    fun getFavoriteUser(): LiveData<List<FavoriteEntry>>? {
        return favorDao?.getFavoriteUser()
    }
}