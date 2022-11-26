package com.shapegames.producer

import com.shapegames.model.IWeatherDataProducer
import com.shapegames.model.CityWeatherData
import com.shapegames.services.OpenWeatherV25Service
import com.shapegames.utils.toCityWeatherData
import java.util.*

class OpenWeatherDataProducer(
    private val weatherService: OpenWeatherV25Service
):IWeatherDataProducer {

    override fun getWeather(cityId: Int): CityWeatherData {

        val openWeatherData = weatherService.getWeatherData(cityId)
        return  openWeatherData.toCityWeatherData(Date())
    }
}