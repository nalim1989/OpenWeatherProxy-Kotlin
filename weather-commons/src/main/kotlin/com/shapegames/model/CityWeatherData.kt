package com.shapegames.model

import java.util.*

data class CityWeatherData(
    val cityId: Int,
    val weatherData: List<WeatherData>,
    val loadTime:Date
)

data class WeatherData(
    val temperature:Float,
    val forecastTime: Date
    //Add weather details such as wind, clouds etc.
)