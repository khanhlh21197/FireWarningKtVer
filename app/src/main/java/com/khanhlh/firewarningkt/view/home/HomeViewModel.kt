package com.khanhlh.firewarningkt.view.home

import androidx.lifecycle.MutableLiveData
import com.khanhlh.firewarningkt.data.local.model.WeatherResponse
import com.khanhlh.firewarningkt.data.repository.WeatherRepository
import com.khanhlh.firewarningkt.helper.extens.init
import com.khanhlh.firewarningkt.helper.extens.logD
import com.khanhlh.firewarningkt.helper.extens.set
import com.khanhlh.firewarningkt.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.*

class HomeViewModel
constructor(private val repo: WeatherRepository) : BaseViewModel() {
    val minTemp = MutableLiveData<String>().init("")
    val maxTemp = MutableLiveData<String>().init("")
    val averageTemp = MutableLiveData<String>().init("")
    val country = MutableLiveData<String>().init("")
    val pressure = MutableLiveData<String>().init("")
    val humidity = MutableLiveData<String>().init("")
    val city = MutableLiveData<String>().init("")
    val sunrise = MutableLiveData<String>().init("")
    val sunset = MutableLiveData<String>().init("")

    fun getCurrentData(lat: String = "35", long: String = "139", clientId: String) =
        repo.getCurrentData(lat, long, clientId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onGetWeatherSuccess(it) }, { onFailure(it) })

    private fun onGetWeatherSuccess(response: Response<WeatherResponse>?) {
        if (response?.body() == null) return
        val weatherResponse = response.body()
        val minTempF = weatherResponse!!.main!!.temp_min
        val maxTempF = weatherResponse.main!!.temp_max
        val humidityF = weatherResponse.main!!.humidity
        val pressureF = weatherResponse.main!!.pressure
        val sunriseL = weatherResponse.sys!!.sunrise
        val sunsetL = weatherResponse.sys!!.sunset
        val sunriseDate = Date(sunriseL.times(1000))
        val sunsetDate = Date(sunsetL.times(1000))

        minTemp.set(minTempF.toString())
        maxTemp.set(maxTempF.toString())
        averageTemp.set(average(minTempF, maxTempF).toString())
        averageTemp.set(weatherResponse.main!!.temp_min.toString())
        humidity.set(humidityF.toString())
        pressure.set(pressureF.toString())
        city.set(weatherResponse.name)
        sunrise.set(sunriseDate.toString())
        sunset.set(sunsetDate.toString())
    }

    private fun onFailure(error: Throwable?) {
        logD(error.toString())
    }

    private fun average(vararg numbers: Float): Float {
        return sum(numbers) / numbers.size
    }

    private fun sum(numbers: FloatArray): Float {
        var sum = 0f;
        for (i in numbers) sum += i
        return sum
    }
}