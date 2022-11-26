package com.shapegames

import com.shapegames.data.WeatherDal

class DatabaseMaintenance(
    private val dal: WeatherDal
) {

    fun deleteInvalidData(){
        dal.deleteInvalid()
    }
}