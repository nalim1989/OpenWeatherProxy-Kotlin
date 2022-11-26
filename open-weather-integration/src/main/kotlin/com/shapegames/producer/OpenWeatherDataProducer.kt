package com.shapegames.producer

import com.shapegames.model.IWeatherDataProducer
import com.shapegames.model.CityWeatherData
import com.shapegames.services.OpenWeatherV25Service
import com.shapegames.utils.toCityWeatherData
import java.util.*

class OpenWeatherDataProducer:IWeatherDataProducer {

    override fun getWeather(cityId: Int): CityWeatherData {

        val openWeatherV25Service = OpenWeatherV25Service()
        val openWeatherData = openWeatherV25Service.getWeatherData(cityId)

        return  openWeatherData.toCityWeatherData(Date())
    }
}