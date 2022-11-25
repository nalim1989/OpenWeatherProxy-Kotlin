package com.shapegames.services

import com.beust.klaxon.Klaxon
import com.shapegames.exceptions.OpenWeatherConnectionException
import com.shapegames.exceptions.OpenWeatherResponseException
import com.shapegames.response.OpenWeatherV25Response
import com.shapegames.rest.RestConnection
import com.shapegames.utils.OpenWeatherV25UrlBuilder
import java.lang.Exception

class OpenWeatherService {

    fun getWeatherData(location:Int):OpenWeatherV25Response{

        val openWeatherUrl = OpenWeatherV25UrlBuilder.buildForecastDataUrl(location)

        try {
            val weatherJson = RestConnection().syncGet(openWeatherUrl)
            Klaxon().parse<OpenWeatherV25Response>(weatherJson)?.let { return  it } ?: throw  OpenWeatherResponseException()
        } catch (e:Exception){
            throw  OpenWeatherConnectionException(e)
        }

    }

}