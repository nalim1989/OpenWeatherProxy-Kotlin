package com.shapegames.response

import com.beust.klaxon.Json

class OpenWeatherV25Response(
    val city: City,

    @Json(name = "list")
    val data: List<TemperatureData>
) {}

class TemperatureData(
    @Json(name = "dt")
    val time: Int,

    @Json(name = "main")
    val temperature: Temperature
) {}

data class Temperature(
    @Json(name = "temp")
    val value: Float
){}

data class City(
    val id: Int,
    val name: String,
    val country: String
){}