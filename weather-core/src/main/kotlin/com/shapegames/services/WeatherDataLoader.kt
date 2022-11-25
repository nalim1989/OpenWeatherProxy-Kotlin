package com.shapegames.services

import com.shapegames.model.IWeatherDataProducer
import com.shapegames.model.TemperatureData
import com.shapegames.model.WeatherData
import com.shapegames.provider.OpenWeatherDataProducer
import com.shapegames.utils.DataQuality

class WeatherDataLoader(
    private val openWeatherDataProducer: OpenWeatherDataProducer
) {

    private val dataProducers:Set<IWeatherDataProducer> = linkedSetOf(openWeatherDataProducer)

    fun loadTemperatureData(cityId:Int):List<TemperatureData>{
        for (dataProducer in dataProducers){
            val weatherData = dataProducer.getWeather(cityId)
            if(weatherData.isNotEmpty() && DataQuality.assertDataIsValid(weatherData)) {
                return weatherData.map { it.temperature }.toList()
            }
        }

        throw  Exception("Can not load data")
    }

}