package com.shapegames.data

import org.jetbrains.exposed.sql.Table

object WeatherTable : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val cityId = integer("city_id")
    val temperature = float("temp")
    val forecastTime = date("forecast_time")
    val loadId = reference("load_id",DataLoadTable.id)
}

object DataLoadTable : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val loadTime = date("load_time")
}