package com.shapegames.model

import java.util.Date

data class CityWeatherData(
    val cityId: Int,
    val weatherData: List<WeatherData>
) {}

data class WeatherData(
    val temperature:Float,
    val forecastTime:Date
    //Add weather details such as wind, clouds etc
){}