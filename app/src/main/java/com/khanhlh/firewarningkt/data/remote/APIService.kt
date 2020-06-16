package com.khanhlh.firewarningkt.data.remote

import com.khanhlh.firewarningkt.constant.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {
        fun getClient(): Retrofit {
            val okHttpClient = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}