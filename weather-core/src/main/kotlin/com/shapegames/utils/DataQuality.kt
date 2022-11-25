package com.shapegames.utils

import com.shapegames.model.WeatherData

class DataQuality {

    companion object{
         fun assertDataIsValid(data: List<WeatherData>):Boolean{
            return true
        }
    }
}