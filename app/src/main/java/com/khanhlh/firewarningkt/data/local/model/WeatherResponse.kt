package com.khanhlh.firewarningkt.data.local.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherResponse {
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null

    @SerializedName("sys")
    @Expose
    var sys: Sys? = null

    @SerializedName("weather")
    @Expose
    var weather = ArrayList<Weather>()

    @SerializedName("main")
    @Expose
    var main: Main? = null

    @SerializedName("wind")
    @Expose
    var wind: Wind? = null

    @SerializedName("rain")
    @Expose
    var rain: Rain? = null

    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null

    @SerializedName("dt")
    @Expose
    var dt = 0f

    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("cod")
    @Expose
    var cod = 0f
}

class Weather {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("main")
    @Expose
    var main: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null
}

class Clouds {
    @SerializedName("all")
    @Expose
    var all = 0f
}

class Rain {
    @SerializedName("3h")
    @Expose
    var h3 = 0f
}

class Wind {
    @SerializedName("speed")
    @Expose
    var speed = 0f

    @SerializedName("deg")
    @Expose
    var deg = 0f
}

class Main {
    @SerializedName("temp")
    @Expose
    var temp = 0f

    @SerializedName("humidity")
    @Expose
    var humidity = 0f

    @SerializedName("pressure")
    @Expose
    var pressure = 0f

    @SerializedName("temp_min")
    @Expose
    var temp_min = 0f

    @SerializedName("temp_max")
    @Expose
    var temp_max = 0f
}

class Sys {
    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("sunrise")
    @Expose
    var sunrise: Long = 0

    @SerializedName("sunset")
    @Expose
    var sunset: Long = 0
}

class Coord {
    @SerializedName("lon")
    @Expose
    var lon = 0f

    @SerializedName("lat")
    @Expose
    var lat = 0f
}