package com.shapegames.utils

import com.shapegames.model.WeatherData

class DataQuality {

    companion object{
        //Check if provided data has values today + 5days
         fun assertDataIsValid(data: List<WeatherData>):Boolean{
            return data.maxBy { it.forecastTime }.forecastTime.after(DateUtils.get5DaysStartOfADay())
        }
    }
}