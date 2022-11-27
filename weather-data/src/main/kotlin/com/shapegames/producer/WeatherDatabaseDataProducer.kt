package com.shapegames.producer

import com.shapegames.data.WeatherDal
import com.shapegames.model.CityWeatherData
import com.shapegames.model.IWeatherDataProducer
import mu.KotlinLogging
import java.lang.Exception

private val logger = KotlinLogging.logger {}

class WeatherDatabaseDataProducer(
    private val dal:WeatherDal
): IWeatherDataProducer {

    override fun getWeather(cityId: Int): CityWeatherData? {
        logger.info { "Database weather data request for $cityId" }
        return try {
            dal.fetchLatestWeather(cityId)
        } catch (e:Exception){
            logger.error ( "Database read exception", e )
            null
        }
    }
}