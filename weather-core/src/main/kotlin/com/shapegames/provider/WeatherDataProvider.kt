package com.shapegames.provider

import com.shapegames.cache.WeatherCache
import com.shapegames.model.WeatherData
import com.shapegames.utils.DataQuality
import com.shapegames.utils.DateUtils
import kotlinx.coroutines.runBlocking

class WeatherDataProvider(
    private val cache: WeatherCache
) {

    fun get5DaysWeather(cityId:Int):List<WeatherData>{
        var fiveDayTemperatures:List<WeatherData>

        // Wait for weather data to be calculated
        runBlocking{
            fiveDayTemperatures = cache.getTemperatureData(cityId)

            //In case there is some data in cache, but it does not contain enough information we need
            //e.g. The data is less than 24h old but does not contain information for today + 5
            //In that case invalidate the cache and do the fetch again
            //WARNING: Recursion is called - if changing this code be aware of potential infinite loop
            //There is DataQuality assertion on the cache load which prevents data to be invalid again after load
            if(!DataQuality.assertDataIsValid(fiveDayTemperatures)){
                cache.invalidateTemperatureData(cityId)
                fiveDayTemperatures=get5DaysWeather(cityId)
            }
        }
        return fiveDayTemperatures
    }

      fun getNextDayWeather(cityId: Int):List<WeatherData>{

          var nextDayTemperatures:List<WeatherData>

          // Wait for weather data to be calculated
          runBlocking{
              val cachedTemperatures:List<WeatherData> = cache.getTemperatureData(cityId)
              nextDayTemperatures = cachedTemperatures.filter { it.forecastTime.after(DateUtils.getTomorrowStartOfADay()) && it.forecastTime.before( DateUtils.getTomorrowEndOfADay()) }

              //We are only looking for the next day temperatures
              //This should never happen, since we invalidate cache 24 hours after write and filling cache with 5-day values
              //WARNING: Recursion is called -  if changing this code be aware of potential infinite loop
              //There is DataQuality assertion on the cache load which prevents data to be invalid again after load
              if(nextDayTemperatures.isEmpty()){
                  cache.invalidateTemperatureData(cityId)
                  nextDayTemperatures = getNextDayWeather(cityId)
              }
          }


        return nextDayTemperatures
    }
}