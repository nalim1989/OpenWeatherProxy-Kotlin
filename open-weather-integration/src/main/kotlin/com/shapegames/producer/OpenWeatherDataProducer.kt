package com.shapegames.producer

import com.shapegames.model.IWeatherDataProducer
import com.shapegames.model.CityWeatherData
import com.shapegames.services.OpenWeatherV25Service
import com.shapegames.utils.toCityWeatherData
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}

class OpenWeatherDataProducer(
    private val weatherService: OpenWeatherV25Service
):IWeatherDataProducer {

    override fun getWeather(cityId: Int): CityWeatherData {
        logger.info { "Open weather data request for $cityId" }
        val openWeatherData = weatherService.getWeatherData(cityId)
        return  openWeatherData.toCityWeatherData(Date())
    }
}