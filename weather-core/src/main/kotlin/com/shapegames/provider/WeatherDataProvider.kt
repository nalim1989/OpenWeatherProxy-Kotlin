package com.shapegames.provider

import com.shapegames.cache.WeatherCache
import com.shapegames.model.TemperatureData
import com.shapegames.utils.DateUtils
import kotlinx.coroutines.runBlocking
import java.util.*

class WeatherDataProvider(
    private val cache: WeatherCache
) {

    fun get5DaysTemperatures(cityId:Int):List<TemperatureData>{
        var fiveDayTemperatures:List<TemperatureData>

        runBlocking{
            fiveDayTemperatures = cache.getTemperatureData(cityId)
            val allDataIsProvided = fiveDayTemperatures.maxBy { it.forecastTime }.forecastTime.after(DateUtils.get5DaysStartOfADay())

            if(!allDataIsProvided){
                cache.invalidateTemperatureData(cityId)
                fiveDayTemperatures=get5DaysTemperatures(cityId)
            }
        }
        return fiveDayTemperatures
    }

      fun getNextDayTemperatures(cityId: Int):List<TemperatureData>{

          var tomorrowTemperatures:List<TemperatureData>

          runBlocking{
              val cachedTemperatures:List<TemperatureData> = cache.getTemperatureData(cityId)
              tomorrowTemperatures = cachedTemperatures.filter { it.forecastTime.after(DateUtils.getTomorrowStartOfADay()) && it.forecastTime.before( DateUtils.getTomorrowEndOfADay()) }

              //This should never happen, since we invalidate cache 24 hours after write
              if(tomorrowTemperatures.isEmpty()){
                  cache.invalidateTemperatureData(cityId)
                  tomorrowTemperatures = getNextDayTemperatures(cityId)
              }
          }


        return tomorrowTemperatures
    }
}