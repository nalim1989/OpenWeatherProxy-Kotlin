package com.shapegames.acceptor

import com.shapegames.data.WeatherDal
import com.shapegames.model.CityWeatherData
import com.shapegames.model.IWeatherDataAcceptor
import com.shapegames.model.WeatherDataAcceptor

class DatabaseWeatherDataAcceptor(
    private val dal: WeatherDal
):IWeatherDataAcceptor {

    init {
        WeatherDataAcceptor.registerAcceptor(this)
    }

    override fun acceptData(data: CityWeatherData) {
        val latestLoad = dal.fetchLatestLoadTime(data.cityId)

        if(data.loadTime.after(latestLoad)){
            dal.insertWeather(data.cityId, data.weatherData, data.loadTime)
        }
    }
}