package com.shapegames.response

data class SummaryResponse(
    val data:SummaryData
)

data class SummaryData(
    val favouritesAbove:Set<Int>
)

data class WeatherForecastResponse(
    val data:List<CityWeatherForecastData>
)

data class CityWeatherForecastData(
    val cityId:Int,
    val weatherData:List<WeatherForecastData>
)

data class WeatherForecastData(
    val temperature:Float,
    val forecastTime:Long
)