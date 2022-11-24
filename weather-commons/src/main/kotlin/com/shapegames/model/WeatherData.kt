package com.shapegames.model

import java.util.Date

data class WeatherData(
    val cityId: Int,
    val temperature: TemperatureData
) {}

data class TemperatureData(
    val temperature:Float,
    val forecastTime:Date
){}