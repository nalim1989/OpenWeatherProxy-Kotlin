package com.shapegames.data

import com.shapegames.model.WeatherData
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toWeatherData(): WeatherData = WeatherData(
    temperature = this[WeatherTable.temperature],
    forecastTime = this[WeatherTable.forecastTime].toDate()
)

fun List<ResultRow>.toWeatherData(): List<WeatherData> =
    this.stream().map { it.toWeatherData() }.toList()

