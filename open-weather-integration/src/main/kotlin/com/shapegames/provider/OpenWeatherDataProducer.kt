package com.shapegames.provider

import com.shapegames.model.IWeatherDataProducer
import com.shapegames.model.WeatherData
import com.shapegames.services.OpenWeatherService
import com.shapegames.utils.toWeatherData

class OpenWeatherDataProducer:IWeatherDataProducer {

    override fun getWeather(cityId: Int): List<WeatherData> {

        val openWeatherService = OpenWeatherService()
        val openWeatherData = openWeatherService.getWeatherData(cityId)

        return  openWeatherData.toWeatherData()
    }
}