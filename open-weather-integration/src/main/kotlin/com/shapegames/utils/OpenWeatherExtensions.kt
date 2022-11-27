package com.shapegames.utils

import com.shapegames.model.CityWeatherData
import com.shapegames.model.WeatherData
import com.shapegames.response.OpenWeatherV25Response
import java.util.*

fun OpenWeatherV25Response.toCityWeatherData(loadTime:Date) =
    CityWeatherData(
        this.city.id,
        //multiply unix-time by 1000, since java is expecting milliseconds
        this.data.map {WeatherData(it.temperature.value, Date(it.time * 1000))}.toList(),
        loadTime
    )

