package com.shapegames.services

import com.shapegames.exceptions.DataLoadException
import com.shapegames.model.CityWeatherData
import com.shapegames.model.IWeatherDataProducer
import com.shapegames.model.WeatherData
import com.shapegames.model.WeatherDataAcceptors
import com.shapegames.producer.OpenWeatherDataProducer
import com.shapegames.producer.WeatherDatabaseDataProducer
import com.shapegames.utils.assertDataIsValid
import mu.KotlinLogging
import kotlin.concurrent.thread

private val logger = KotlinLogging.logger {}
class WeatherDataLoader(
    private val openWeatherDataProducer: OpenWeatherDataProducer,
    private val weatherDatabaseDataProducer: WeatherDatabaseDataProducer
) {

    //The order is important, try local storage first and then switch to more "expensive" options
    private val dataProducers:Set<IWeatherDataProducer> = linkedSetOf(openWeatherDataProducer,weatherDatabaseDataProducer)

    fun loadTemperatureData(cityId:Int):List<WeatherData>{
        for (dataProducer in dataProducers){
            val cityWeatherData:CityWeatherData
            try{
                cityWeatherData = dataProducer.getWeather(cityId)
            } catch (e:Exception){ // Try another producer
                logger.warn( "Weather data producer not working", e )
                continue
            }

            // If data is valid(expected) return it, if not continue to fetch from another producer
            if(assertDataIsValid(cityWeatherData.weatherData)) {
                //Do not want to wait for all those tasks to finish
                //Do not want to failure in the data storage affects current thread
                thread {
                    WeatherDataAcceptors.sendNewDataNotification(cityWeatherData)
                }.start()
                return cityWeatherData.weatherData
            }
        }

        //In case the valid data can not be loaded for any of producers
        throw  DataLoadException("Not able to find any producer with valid data")
    }

}