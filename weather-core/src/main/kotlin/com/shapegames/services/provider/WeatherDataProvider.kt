package com.shapegames.services.provider

import com.shapegames.model.IWeatherDataProducer
import com.shapegames.model.TemperatureData
import com.shapegames.provider.OpenWeatherDataProducer

class WeatherDataProvider {

    fun get5DaysTemperatures(cityId:Int):List<TemperatureData>{
        return listOf()
    }

    fun getNextDayTemperatures(cityId: Int):List<TemperatureData>{
        return listOf()
    }
}