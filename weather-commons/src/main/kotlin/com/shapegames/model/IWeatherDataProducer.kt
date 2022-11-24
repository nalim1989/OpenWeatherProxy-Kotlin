package com.shapegames.model

interface IWeatherDataProducer {

    fun getWeather(cityId:Int):List<WeatherData>
}