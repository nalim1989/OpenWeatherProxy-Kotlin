package com.shapegames.utils

import com.shapegames.model.TemperatureData
import com.shapegames.model.WeatherData
import com.shapegames.response.OpenWeatherV25Response
import java.util.*

fun OpenWeatherV25Response.toWeatherData():List<WeatherData>{
    return this.data.stream().map { d ->  WeatherData(this.city.id, TemperatureData(d.temperature.value, Date(d.time)))}.toList()
}
