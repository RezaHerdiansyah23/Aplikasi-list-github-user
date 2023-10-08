package com.example.aplikasigithubuser.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.aplikasigithubuser.api.APIConfig
import com.example.aplikasigithubuser.database.FavoriteDao
import com.example.aplikasigithubuser.database.FavoriteEntry
import com.example.aplikasigithubuser.database.FavoriteRoomDatabase
import com.example.aplikasigithubuser.model.DetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailResponse>()


    private var favorDao: FavoriteDao?
    private var userDb: FavoriteRoomDatabase?
    init {
        userDb = FavoriteRoomDatabase.getDatabase(application)
        favorDao = userDb?.favDao()

    }



    fun setUserDetail(username: String) {
        APIConfig.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteEntry(
                username,
                id,
                avatarUrl
            )
            favorDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = favorDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favorDao?.removeFromFavorite(id)
        }
    }
}
