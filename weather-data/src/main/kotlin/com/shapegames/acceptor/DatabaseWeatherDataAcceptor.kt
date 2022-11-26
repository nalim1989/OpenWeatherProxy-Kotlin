package com.shapegames.acceptor

import com.shapegames.data.WeatherDal
import com.shapegames.model.CityWeatherData
import com.shapegames.model.IWeatherDataAcceptor
import com.shapegames.model.WeatherDataAcceptors

class DatabaseWeatherDataAcceptor(
    private val dal: WeatherDal
):IWeatherDataAcceptor {

    init {
        WeatherDataAcceptors.registerAcceptor(this)
    }

    override fun acceptCityData(cityData: CityWeatherData) {
        val latestLoad = dal.fetchLatestLoad(cityData.cityId)

        if(cityData.loadTime.after(latestLoad.loadTime)){
            dal.insertWeather(cityData.cityId, cityData.weatherData, cityData.loadTime)

        }
    }
}