package com.example.aplikasigithubuser.api
import com.example.aplikasigithubuser.model.DetailResponse
import com.example.aplikasigithubuser.model.UserResponse
import com.example.aplikasigithubuser.model.user
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    companion object {
        object AuthToken {
            const val TOKEN = "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"
        }
    }

    @GET("search/users")
    @Headers("Authorization: token ${AuthToken.TOKEN}")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${AuthToken.TOKEN}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${AuthToken.TOKEN}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<user>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${AuthToken.TOKEN}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<user>>

}
