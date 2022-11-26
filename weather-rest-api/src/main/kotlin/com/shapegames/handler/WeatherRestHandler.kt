package com.shapegames.handler

import com.shapegames.model.TemperatureUnits
import com.shapegames.response.CityWeatherForecastData
import com.shapegames.response.SummaryResponse
import com.shapegames.response.WeatherForecastResponse
import com.shapegames.services.WeatherService
import com.shapegames.utils.fahrenheitToCelsius
import com.shapegames.utils.toSummaryResponse
import com.shapegames.utils.toWeatherForecastData
import com.shapegames.validation.validateFloatParam
import com.shapegames.validation.validateIntegerListParam
import com.shapegames.validation.validateTemperatureUnitsParam

class WeatherRestHandler(
    private val weatherService: WeatherService
) {

    fun handleTemperatureSummaryRequest(cityIds:List<String>, temperature:String?, units:String?): SummaryResponse {
        val validatedCityIds = validateIntegerListParam(param = cityIds, paramName = "cityIds")
        val validatedTemperature = validateFloatParam(param = temperature, paramName = "temperature")
        val validatedTemperatureUnits = validateTemperatureUnitsParam(param=units, "units")

        val tempInCelsius = if(validatedTemperatureUnits==TemperatureUnits.CELSIUS) validatedTemperature
        else fahrenheitToCelsius(validatedTemperature)

        val summary = weatherService.getFavouritesAboveTemp(tempInCelsius, validatedCityIds.toHashSet())

        return summary.toSummaryResponse()
    }

    fun handleWeatherRequest(cityIds:List<String>): WeatherForecastResponse {
        val validatedCityIds = validateIntegerListParam(param = cityIds, paramName = "cityIds")

        val cityForecasts:MutableList<CityWeatherForecastData> = mutableListOf()
        //TODO think about parallelism
        validatedCityIds.stream().forEach{
            val forecastData = weatherService.get5DayWeather(it).toWeatherForecastData()
            cityForecasts.add(CityWeatherForecastData(it, forecastData))
        }

        return WeatherForecastResponse(cityForecasts)
    }
}