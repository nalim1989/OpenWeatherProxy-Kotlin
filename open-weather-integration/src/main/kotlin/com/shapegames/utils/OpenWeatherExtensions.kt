package com.shapegames.utils

import com.shapegames.model.WeatherData
import com.shapegames.model.CityWeatherData
import com.shapegames.response.OpenWeatherV25Response
import java.util.*

fun OpenWeatherV25Response.toWeatherData():CityWeatherData{
    return CityWeatherData(this.city.id,this.data.stream().map { d ->  WeatherData(d.temperature.value, Date(d.time))}.toList())
}
