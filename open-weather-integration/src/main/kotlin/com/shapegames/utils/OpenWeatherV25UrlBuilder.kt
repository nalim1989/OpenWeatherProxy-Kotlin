package com.shapegames.utils

import com.shapegames.constants.OPEN_WEATHER_API_KEY
import com.shapegames.constants.OPEN_WEATHER_BASE_URL

class OpenWeatherV25UrlBuilder {
    companion object{

        private const val VERSION = 2.5
        fun buildForecastDataUrl(location:Int):String{

            val url = StringBuilder("${OPEN_WEATHER_BASE_URL}/data/${VERSION}/forecast")
            url.append("?")
            url.append("id=").append(location)
            url.append("&")
            url.append("appid=").append(OPEN_WEATHER_API_KEY)

            return url.toString()
        }
    }
}