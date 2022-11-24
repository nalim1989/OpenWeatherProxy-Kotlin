package com.shapegames.services

import com.beust.klaxon.Klaxon
import com.shapegames.response.OpenWeatherV25Response
import com.shapegames.rest.RestConnection
import com.shapegames.utils.OpenWeatherV25UrlBuilder
import java.lang.Exception

class OpenWeatherService {

    fun getWeatherData(location:Int):OpenWeatherV25Response{

        val openWeatherUrl = OpenWeatherV25UrlBuilder.buildForecastDataUrl(location)
        val weatherJson = RestConnection().syncGet(openWeatherUrl)

        val weatherData:OpenWeatherV25Response? = try {
            Klaxon().parse<OpenWeatherV25Response>(weatherJson)
        } catch (e:Exception){
            // TODO handle the exception
            throw  e
        }

        return weatherData?.let { return it } ?: throw Exception("") //TODO handle the exception
    }

}