package com.khanhlh.firewarningkt.data.repository

import com.khanhlh.firewarningkt.data.local.model.WeatherResponse
import com.khanhlh.firewarningkt.data.remote.weather.WeatherService
import io.reactivex.Single
import retrofit2.Response

class WeatherRepository(private val weatherService: WeatherService) {
    fun getCurrentData(
        lat: String,
        long: String,
        clientId: String
    ): Single<Response<WeatherResponse>> =
        weatherService.getCurrentData(lat, long, clientId)
}