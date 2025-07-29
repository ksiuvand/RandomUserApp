package com.example.randomuserapp.data.api

import com.example.randomuserapp.data.entities.RandomUsersResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET(".")
    suspend fun getUsers(
        @Query("results") count: Int
    ): RandomUsersResponse

    companion object {
        private const val BASE_URL = "https://randomuser.me/api/"

        @Volatile
        private var instance: RandomUserApi? = null

        fun getInstance(): RandomUserApi {
            return instance ?: synchronized(this) {
                instance ?: Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RandomUserApi::class.java)
                    .also { instance = it }
            }
        }
    }
}