package com.shapegames.services

import com.shapegames.exceptions.DataLoadException
import com.shapegames.model.CityWeatherData
import com.shapegames.model.IWeatherDataProducer
import com.shapegames.model.WeatherData
import com.shapegames.model.WeatherDataAcceptors
import com.shapegames.utils.assertDataIsValid
import mu.KotlinLogging
import kotlin.concurrent.thread

private val logger = KotlinLogging.logger {}
class WeatherDataLoader(
    //The order is important
    private val dataProducers:Set<IWeatherDataProducer>
) {
    fun loadWeatherData(cityId:Int):List<WeatherData>{
        for (dataProducer in dataProducers){
            val cityWeatherData:CityWeatherData?
            try{
                cityWeatherData = dataProducer.getWeather(cityId)
            } catch (e:Exception){ // Try another producer
                logger.warn( "Weather data producer not working", e )
                continue
            }

            // If data is valid(expected) return it, if not continue to fetch from another producer
            if(assertDataIsValid(cityWeatherData)) {
                logger.info { "Data accepted" }
                //Do not want to wait for all those tasks to finish
                //Do not want to failure in the data storage affects current thread
                thread {
                    WeatherDataAcceptors.sendNewDataNotification(cityWeatherData!!) //Data is validated
                }
                return cityWeatherData!!.weatherData //Data is validated
            } else {
                logger.info { "Data is not valid. Try another producer" }
            }
        }

        //In case the valid data can not be loaded for any of producers
        throw  DataLoadException("Not able to find any producer with valid data")
    }

}