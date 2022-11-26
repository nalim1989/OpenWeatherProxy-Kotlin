package com.shapegames.utils

import com.shapegames.model.WeatherData
import com.shapegames.response.*

fun Set<Int>.toSummaryResponse()=
    SummaryResponse(SummaryData(this))

fun WeatherData.toWeatherForecastData()=
    WeatherForecastData( this.temperature,this.forecastTime.time)

fun List<WeatherData>.toWeatherForecastData()=
   this.map { it.toWeatherForecastData() }.toList()