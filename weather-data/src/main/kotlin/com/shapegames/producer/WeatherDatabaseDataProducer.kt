package com.shapegames.producer

import com.shapegames.data.WeatherDal
import com.shapegames.model.CityWeatherData
import com.shapegames.model.IWeatherDataProducer

class WeatherDatabaseDataProducer(
    private val dal:WeatherDal
): IWeatherDataProducer {

    override fun getWeather(cityId: Int): CityWeatherData {
        return dal.fetchLatestWeather(cityId)
    }
}