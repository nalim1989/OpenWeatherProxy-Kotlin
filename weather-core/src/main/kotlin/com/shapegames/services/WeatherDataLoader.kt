package com.shapegames.services

import com.shapegames.model.CityWeatherData
import com.shapegames.model.IWeatherDataProducer
import com.shapegames.model.WeatherData
import com.shapegames.producer.OpenWeatherDataProducer
import com.shapegames.utils.DataQuality

class WeatherDataLoader(
    private val openWeatherDataProducer: OpenWeatherDataProducer
) {

    //The order is important, try local storage first and then switch to more "expensive" options
    private val dataProducers:Set<IWeatherDataProducer> = linkedSetOf(openWeatherDataProducer)

    fun loadTemperatureData(cityId:Int):List<WeatherData>{
        for (dataProducer in dataProducers){
            val cityWeatherData:CityWeatherData = dataProducer.getWeather(cityId)

            // If data is valid(expected) return it, if not continue to fetch from another producer
            if(DataQuality.assertDataIsValid(cityWeatherData.weatherData)) {
                return cityWeatherData.weatherData
            }
        }

        //In case the valid data can not be loaded for any of producers
        throw  Exception("Can not load data")
    }

}