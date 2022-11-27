package com.shapegames.utils

import com.shapegames.model.WeatherData

//Check if provided data has values today + 5days
fun assertDataIsValid(data: List<WeatherData>):Boolean{
    return data.isNotEmpty() && data.maxBy { it.forecastTime }.forecastTime.after(get5DaysStartOfADay())
}

