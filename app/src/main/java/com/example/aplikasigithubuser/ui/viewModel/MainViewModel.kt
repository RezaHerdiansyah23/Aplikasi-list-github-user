package com.example.aplikasigithubuser.ui.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.aplikasigithubuser.api.APIConfig
import com.example.aplikasigithubuser.database.SettingPreferences
import com.example.aplikasigithubuser.model.UserResponse
import com.example.aplikasigithubuser.model.user
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<user>>()

    fun setSearchUser(query: String) {
        APIConfig.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getSearchUser(): LiveData<ArrayList<user>> {
        return listUsers
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}