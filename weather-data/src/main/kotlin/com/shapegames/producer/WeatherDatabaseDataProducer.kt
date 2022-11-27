package com.shapegames.producer

import com.shapegames.data.WeatherDal
import com.shapegames.model.CityWeatherData
import com.shapegames.model.IWeatherDataProducer
import mu.KotlinLogging
import java.util.*
import kotlin.NoSuchElementException

private val logger = KotlinLogging.logger {}

class WeatherDatabaseDataProducer(
    private val dal:WeatherDal
): IWeatherDataProducer {

    override fun getWeather(cityId: Int): CityWeatherData {
        logger.info { "Database weather data request for $cityId" }
        return try {
            dal.fetchLatestWeather(cityId)
        } catch (e:NoSuchElementException){
            CityWeatherData(cityId, listOf(), Date())
        }
    }
}