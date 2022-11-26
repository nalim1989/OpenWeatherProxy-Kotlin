package com.shapegames.data

import com.shapegames.model.WeatherData
import com.shapegames.utils.get24HoursFromNowTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.Date

class WeatherDal(private val db: Database) {

    fun fetchWeather(cityId: Int): List<WeatherData> {
        val later24hours = get24HoursFromNowTime(Date())
        val weatherJoin = Join(
            WeatherTable, DataLoadTable,
            onColumn = WeatherTable.loadId, otherColumn = DataLoadTable.id,
            joinType = JoinType.INNER)
        // transaction(db) runs the internal query as a new database transaction.
        return transaction(db) {
            // Returns the first invoice with matching id.
            weatherJoin
                .select { WeatherTable.cityId.eq(cityId) and DataLoadTable.loadTime.lessEq(DateTime(later24hours))}
                .toList().toWeatherData()
        }
    }

    fun insertWeather(cityId: Int, data:List<WeatherData>, loadTime:Date){
        transaction(db) {
            val loadId = DataLoadTable.insert {
                it[this.loadTime] = DateTime(loadTime)
            } get DataLoadTable.id

            WeatherTable.batchInsert(data) {
                this[WeatherTable.cityId] = cityId
                this[WeatherTable.temperature] = it.temperature
                this[WeatherTable.forecastTime] = DateTime(it.forecastTime)
                this[WeatherTable.loadId] = loadId
            }

        }
    }
}