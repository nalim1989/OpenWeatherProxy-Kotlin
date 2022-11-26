package com.shapegames.model

object WeatherDataAcceptors {

    private val acceptors:MutableList<IWeatherDataAcceptor> = mutableListOf()

    fun registerAcceptor(acceptor:IWeatherDataAcceptor){
        acceptors.add(acceptor)
    }

    fun sendNewDataNotification(data:CityWeatherData){
        acceptors.parallelStream().forEach { it.acceptCityData(data) }
    }
}

interface IWeatherDataAcceptor{
    fun acceptCityData(cityData:CityWeatherData)
}