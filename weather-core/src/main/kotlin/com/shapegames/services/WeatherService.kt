package com.shapegames.services

import com.shapegames.model.WeatherData
import com.shapegames.provider.WeatherDataProvider
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class WeatherService(
    private val weatherDataProvider: WeatherDataProvider
) {

   fun get5DayWeather(cityId:Int):List<WeatherData> {
        return weatherDataProvider.get5DaysWeather(cityId)
   }

    fun getFavouritesAboveTemp(temperature:Float, cityIds:Set<Int>):Set<Int>{
        val citiesWithHigherTemp = mutableSetOf<Int>()

        //Check of temperatures can be done in parallel since it is an independent operation
        cityIds.parallelStream().forEach {
            val nextDayWeather = weatherDataProvider.getNextDayWeather(it)
            logger.info { "Next day weather:" + nextDayWeather.map { w -> w.temperature }.toList() }
            // There is at least one moment tomorrow then temperature will be above requested temperature
             val higherTempExists = nextDayWeather.any{ cityTemp -> cityTemp.temperature > temperature}
             if(higherTempExists) citiesWithHigherTemp.add(it) //If there is such, add the city to the result
        }

        return citiesWithHigherTemp
    }
}