package com.shapegames.response

import com.beust.klaxon.Json

class OpenWeatherV25Response(
    val city: OpenWeatherV25City,

    @Json(name = "list")
    val data: List<OpenWeatherV25TemperatureData>
) {}

class OpenWeatherV25TemperatureData(
    @Json(name = "dt")
    val time: Long,

    @Json(name = "main")
    val temperature: OpenWeatherV25Temperature
) {}

data class OpenWeatherV25Temperature(
    @Json(name = "temp")
    val value: Float
){}

data class OpenWeatherV25City(
    val id: Int,
    val name: String,
    val country: String
){}