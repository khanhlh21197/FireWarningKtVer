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

class AccuWeatherResponse {
    @SerializedName("Headline")
    @Expose
    var headline: Headline? = null

    @SerializedName("DailyForecasts")
    @Expose
    var dailyForecasts: List<DailyForecast>? = null

}

class DailyForecast {
    @SerializedName("Date")
    @Expose
    var date: String? = null

    @SerializedName("EpochDate")
    @Expose
    var epochDate: Int? = null

    @SerializedName("Temperature")
    @Expose
    var temperature: Temperature? = null

    @SerializedName("Day")
    @Expose
    var day: Day? = null

    @SerializedName("Night")
    @Expose
    var night: Night? = null

    @SerializedName("Sources")
    @Expose
    var sources: List<String>? = null

    @SerializedName("MobileLink")
    @Expose
    var mobileLink: String? = null

    @SerializedName("Link")
    @Expose
    var link: String? = null

}

class Day {
    @SerializedName("Icon")
    @Expose
    var icon: Int? = null

    @SerializedName("IconPhrase")
    @Expose
    var iconPhrase: String? = null

    @SerializedName("HasPrecipitation")
    @Expose
    var hasPrecipitation: Boolean? = null

    @SerializedName("PrecipitationType")
    @Expose
    var precipitationType: String? = null

    @SerializedName("PrecipitationIntensity")
    @Expose
    var precipitationIntensity: String? = null

}

class Headline {
    @SerializedName("EffectiveDate")
    @Expose
    var effectiveDate: String? = null

    @SerializedName("EffectiveEpochDate")
    @Expose
    var effectiveEpochDate: Int? = null

    @SerializedName("Severity")
    @Expose
    var severity: Int? = null

    @SerializedName("Text")
    @Expose
    var text: String? = null

    @SerializedName("Category")
    @Expose
    var category: String? = null

    @SerializedName("EndDate")
    @Expose
    var endDate: String? = null

    @SerializedName("EndEpochDate")
    @Expose
    var endEpochDate: Int? = null

    @SerializedName("MobileLink")
    @Expose
    var mobileLink: String? = null

    @SerializedName("Link")
    @Expose
    var link: String? = null

}

class Maximum {
    @SerializedName("Value")
    @Expose
    var value: Int? = null

    @SerializedName("Unit")
    @Expose
    var unit: String? = null

    @SerializedName("UnitType")
    @Expose
    var unitType: Int? = null

}

class Minimum {
    @SerializedName("Value")
    @Expose
    var value: Int? = null

    @SerializedName("Unit")
    @Expose
    var unit: String? = null

    @SerializedName("UnitType")
    @Expose
    var unitType: Int? = null

}

class Night {
    @SerializedName("Icon")
    @Expose
    var icon: Int? = null

    @SerializedName("IconPhrase")
    @Expose
    var iconPhrase: String? = null

    @SerializedName("HasPrecipitation")
    @Expose
    var hasPrecipitation: Boolean? = null

    @SerializedName("PrecipitationType")
    @Expose
    var precipitationType: String? = null

    @SerializedName("PrecipitationIntensity")
    @Expose
    var precipitationIntensity: String? = null

}

class Temperature {
    @SerializedName("Minimum")
    @Expose
    var minimum: Minimum? = null

    @SerializedName("Maximum")
    @Expose
    var maximum: Maximum? = null

}