package com.khanhlh.firewarningkt.data.remote.weather

import com.khanhlh.firewarningkt.constant.Constants
import com.khanhlh.firewarningkt.data.remote.user.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface WeatherService {
    companion object {
        private val okHttpClient = OkHttpClient.Builder().build()
        const val REGISTER_CAPTAIN_EYE = "account/register";
        fun create(): UserService {
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.WHEATHER_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(UserService::class.java)
        }
    }
}