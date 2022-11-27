package com.shapegames.provider

import com.shapegames.cache.WeatherCache
import com.shapegames.model.WeatherData
import com.shapegames.utils.assertDataIsValid
import com.shapegames.utils.getTomorrowEndOfADay
import com.shapegames.utils.getTomorrowStartOfADay
import kotlinx.coroutines.runBlocking

class WeatherDataProvider(
    private val cache: WeatherCache
) {
    fun get5DaysWeather(cityId:Int):List<WeatherData>{
        var fiveDayTemperatures:List<WeatherData>

        // Wait for weather data to be calculated
        runBlocking{
            //If the data is already not in the cache, it loads it from external producers
            fiveDayTemperatures = cache.getWeatherData(cityId)

            //In case there is some data in cache, but it does not contain enough information we need
            //e.g. The data is less than 24h old but does not contain information for today + 5
            //In that case invalidate the cache and do the fetch again
            //There is DataQuality assertion on the cache load which prevents data to be invalid again after load
            if(!assertDataIsValid(fiveDayTemperatures)){
                cache.invalidateWeatherData(cityId)
                fiveDayTemperatures=get5DaysWeather(cityId)
            }
        }
        return fiveDayTemperatures
    }

      fun getNextDayWeather(cityId: Int):List<WeatherData>{

          var nextDayTemperatures:List<WeatherData>

          // Wait for weather data to be calculated
          runBlocking{
              //If the data is already not in the cache, it loads it from external producers
              val cachedTemperatures = cache.getWeatherData(cityId)
              nextDayTemperatures = cachedTemperatures.filter { it.forecastTime.after(getTomorrowStartOfADay()) && it.forecastTime.before( getTomorrowEndOfADay()) }

              //We are only looking for the next day temperatures
              //This should never happen, since we invalidate cache 24 hours after write and filling cache with 5-day values
              //There is DataQuality assertion on the cache load which prevents data to be invalid again after load
              if(nextDayTemperatures.isEmpty()){
                  cache.invalidateWeatherData(cityId)
                  nextDayTemperatures = getNextDayWeather(cityId)
              }
          }

        return nextDayTemperatures
    }

}