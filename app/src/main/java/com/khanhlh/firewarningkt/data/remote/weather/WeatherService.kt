package com.khanhlh.firewarningkt.data.remote.weather

import com.khanhlh.firewarningkt.constant.Constants
import com.khanhlh.firewarningkt.data.local.model.WeatherResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    companion object {
        private val okHttpClient = OkHttpClient.Builder().build()
        fun create(): WeatherService {
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.WEATHER_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WeatherService::class.java)
        }
    }

    @GET("weather?")
    fun getCurrentData(
        @Query("lat") lat: String,
        @Query("lon") long: String,
        @Query("APPID") clientID: String = Constants.WEATHER_API_KEY,
        @Query("units") unit: String = Constants.cDegree
    ): Single<Response<WeatherResponse>>
}