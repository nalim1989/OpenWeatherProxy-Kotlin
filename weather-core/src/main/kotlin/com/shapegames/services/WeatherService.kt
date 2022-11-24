package com.shapegames.services

import com.shapegames.model.TemperatureData
import com.shapegames.services.provider.WeatherDataProvider

class WeatherService(
    private val weatherDataProvider: WeatherDataProvider
) {

   fun get5DayTemps(cityId:Int):List<TemperatureData> {
        return weatherDataProvider.get5DaysTemperatures(cityId)
   }

    fun getFavouritesAbove(temperature:Int, cityIds:Set<Int>):Set<Int>{
        val citiesWithHigherTemp = mutableSetOf<Int>()
        cityIds.parallelStream().forEach {
             val higherTempExists = weatherDataProvider.getNextDayTemperatures(it).any{cityTemp -> cityTemp.temperature > temperature}
             if(higherTempExists) citiesWithHigherTemp.add(it)
        }

        return citiesWithHigherTemp
    }
}