package com.shapegames.cache

import com.shapegames.model.TemperatureData
import com.shapegames.services.WeatherDataLoader
import io.github.reactivecircus.cache4k.Cache
import kotlin.time.Duration.Companion.hours

const val MAX_CACHE_SIZE = 1000

class WeatherCache(
    private val dataLoader: WeatherDataLoader
) {

    private val temperatureCache:Cache<Int, List<TemperatureData>> = Cache.Builder()
        .maximumCacheSize(1000)
        .expireAfterWrite(24.hours)
        .build()

    suspend fun getTemperatureData(cityId:Int):List<TemperatureData>{
        return temperatureCache.get(cityId) {
                dataLoader.loadTemperatureData(cityId)
        }
    }

    fun invalidateTemperatureData(cityId: Int){
        temperatureCache.invalidate(cityId)
    }

}