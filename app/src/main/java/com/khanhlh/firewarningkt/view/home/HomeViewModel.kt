package com.khanhlh.firewarningkt.view.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.khanhlh.firewarningkt.data.local.model.WeatherResponse
import com.khanhlh.firewarningkt.data.repository.WeatherRepository
import com.khanhlh.firewarningkt.helper.extens.init
import com.khanhlh.firewarningkt.helper.extens.logD
import com.khanhlh.firewarningkt.helper.extens.set
import com.khanhlh.firewarningkt.helper.utils.oneDigit
import com.khanhlh.firewarningkt.helper.utils.toHour
import com.khanhlh.firewarningkt.helper.utils.unixToDate
import com.khanhlh.firewarningkt.viewmodel.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class HomeViewModel
constructor(private val repo: WeatherRepository) : BaseViewModel() {
    val minTemp = MutableLiveData<String>().init("")
    val maxTemp = MutableLiveData<String>().init("")
    val averageTemp = MutableLiveData<String>().init("")
    val country = MutableLiveData<String>().init("")
    val windSpeed = MutableLiveData<String>().init("")
    val pressure = MutableLiveData<String>().init("")
    val humidity = MutableLiveData<String>().init("")
    val city = MutableLiveData<String>().init("")
    val sunrise = MutableLiveData<String>().init("")
    val sunset = MutableLiveData<String>().init("")
    val description = MutableLiveData<String>().init("")
    val currentTime = MutableLiveData<String>().init("")

    fun getCurrentData(lat: String = "35", long: String = "139", clientId: String, unit: String) =
        repo.getCurrentData(lat, long, clientId, unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { startLoad() }
            .doAfterTerminate { stopLoad() }
            .subscribe({ onGetWeatherSuccess(it) }, { onFailure(it) })

    @SuppressLint("DefaultLocale")
    private fun onGetWeatherSuccess(response: Response<WeatherResponse>?) {
        if (response?.body() == null) return
        val weatherResponse = response.body()
        val minTempF = weatherResponse!!.main!!.temp_min
        val maxTempF = weatherResponse.main!!.temp_max
        val humidityF = weatherResponse.main!!.humidity
        val windSpeed = weatherResponse.wind!!.speed
        val pressureF = weatherResponse.main!!.pressure
        val sunriseL = weatherResponse.sys!!.sunrise
        val sunsetL = weatherResponse.sys!!.sunset
        val sunriseDate = sunriseL.unixToDate().toHour()
        val sunsetDate = sunsetL.unixToDate().toHour()

        minTemp.set(
            minTempF.oneDigit()
                    + " " + 0x00B0.toChar()
        )
        maxTemp.set(
            maxTempF.oneDigit()
                    + " " + 0x00B0.toChar()
        )
        averageTemp.set(
            average(minTempF, maxTempF).oneDigit()
                    + " " + 0x00B0.toChar()
        )
        humidity.set(humidityF.toString())
        pressure.set(pressureF.toString())
        city.set(weatherResponse.name)
        sunrise.set(sunriseDate)
        sunset.set(sunsetDate)
        this.windSpeed.set(windSpeed.toString())
        description.set(weatherResponse.weather[0].description!!.capitalize())
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

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun currentTime(): String =
        LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))


    @RequiresApi(Build.VERSION_CODES.O)
    fun tick() {
        Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { currentTime.set(currentTime()) }
    }
}