package com.khanhlh.firewarningkt.data.local.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Clouds {
    @SerializedName("all")
    @Expose
    var all: Int? = null

}

class Coord {
    @SerializedName("lon")
    @Expose
    var lon: Double? = null

    @SerializedName("lat")
    @Expose
    var lat: Double? = null

}

class Example {
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null

    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null

    @SerializedName("base")
    @Expose
    var base: String? = null

    @SerializedName("main")
    @Expose
    var main: Main? = null

    @SerializedName("visibility")
    @Expose
    var visibility: Int? = null

    @SerializedName("wind")
    @Expose
    var wind: Wind? = null

    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null

    @SerializedName("dt")
    @Expose
    var dt: Int? = null

    @SerializedName("sys")
    @Expose
    var sys: Sys? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("cod")
    @Expose
    var cod: Int? = null

}

class Main {
    @SerializedName("temp")
    @Expose
    var temp: Double? = null

    @SerializedName("pressure")
    @Expose
    var pressure: Int? = null

    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null

    @SerializedName("temp_min")
    @Expose
    var tempMin: Double? = null

    @SerializedName("temp_max")
    @Expose
    var tempMax: Double? = null

}

class Sys {
    @SerializedName("type")
    @Expose
    var type: Int? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("message")
    @Expose
    var message: Double? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("sunrise")
    @Expose
    var sunrise: Int? = null

    @SerializedName("sunset")
    @Expose
    var sunset: Int? = null

}

class Weather {
    @SerializedName("id")
    @Expose
    var id: Int? = null

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

class Wind {
    @SerializedName("speed")
    @Expose
    var speed: Double? = null

    @SerializedName("deg")
    @Expose
    var deg: Int? = null

}