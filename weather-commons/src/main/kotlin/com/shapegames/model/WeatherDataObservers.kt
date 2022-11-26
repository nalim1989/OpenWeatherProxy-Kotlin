package com.shapegames.model

object WeatherDataAcceptor {

    private val acceptors:MutableList<IWeatherDataAcceptor> = mutableListOf()

    fun registerAcceptor(acceptor:IWeatherDataAcceptor){
        acceptors.add(acceptor)
    }

    fun sendNewDataNotification(data:CityWeatherData){
        acceptors.parallelStream().forEach { it.acceptData(data) }
    }
}

interface IWeatherDataAcceptor{
    fun acceptData(data:CityWeatherData)
}