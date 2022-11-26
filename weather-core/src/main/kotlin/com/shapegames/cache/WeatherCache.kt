package com.shapegames.cache

import com.shapegames.model.WeatherData
import com.shapegames.constants.DATA_EXPIRATION
import com.shapegames.services.WeatherDataLoader
import io.github.reactivecircus.cache4k.Cache

//Current cache size is set to 1000, but could be tuned according to the statistics
const val MAX_CACHE_SIZE = 1000L

class WeatherCache(
    private val dataLoader: WeatherDataLoader
) {

    //https://github.com/ReactiveCircus/cache4k
    private val temperatureCache:Cache<Int, List<WeatherData>> = Cache.Builder()
        .maximumCacheSize(MAX_CACHE_SIZE)
        .expireAfterWrite(DATA_EXPIRATION)
        .build()


    suspend fun getTemperatureData(cityId:Int):List<WeatherData>{
        //From the documentation:
        //Note that loader is executed on the caller's coroutine context. Concurrent calls from multiple threads using the same key will be blocked.
        //Assuming the 1st call successfully computes a new value, none of the loader from the other calls will be executed and the cached
        //value computed by the first loader will be returned for those calls.

        //Any exceptions thrown by the loader will be propagated to the caller of this function.
        return temperatureCache.get(cityId) {
                dataLoader.loadTemperatureData(cityId)
        }
    }

    fun invalidateTemperatureData(cityId: Int){
        temperatureCache.invalidate(cityId)
    }

}