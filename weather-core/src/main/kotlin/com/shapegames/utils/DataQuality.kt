package com.shapegames.utils

import com.shapegames.constants.DATA_EXPIRATION
import com.shapegames.model.CityWeatherData
import com.shapegames.model.WeatherData
import java.util.*


fun assertDataIsValid(data:CityWeatherData?)=
        data != null &&
        data.weatherData.isNotEmpty() &&
        dataIsNoDeprecated(data) &&
        dataContainsNecessaryInformation(data.weatherData)


//Check if provided data has values today + 5days
fun dataContainsNecessaryInformation(data: List<WeatherData>)=
     data.maxBy { it.forecastTime }.forecastTime.after(get5DaysStartOfADay())


fun dataIsNoDeprecated(data: CityWeatherData)=
     data.loadTime.before(getTimeAfter(Date(), DATA_EXPIRATION))

