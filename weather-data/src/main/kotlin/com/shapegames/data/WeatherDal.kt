package com.shapegames.data

import com.shapegames.model.CityWeatherData
import com.shapegames.model.WeatherData
import com.shapegames.model.WeatherDataLoad
import com.shapegames.utils.get24HoursFromNowTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.Date

class WeatherDal(private val db: Database) {

    fun fetchLatestWeather(cityId: Int): CityWeatherData {
        transaction(db) {
            val latestLoad = fetchLatestLoad(cityId)
            val weatherData = WeatherTable
                .select { WeatherTable.loadId.eq(latestLoad.id)}
                .toList().toWeatherData()

            return@transaction CityWeatherData(cityId,weatherData,latestLoad.loadTime)
        }

         throw Exception("Something went wrong")
    }

    fun fetchLatestLoad(cityId: Int): WeatherDataLoad {
        val later24hours = get24HoursFromNowTime(Date())
        val weatherJoin = getWeatherLoadJoin()

        transaction(db) {

            return@transaction weatherJoin
                .slice(DataLoadTable.loadTime)
                .select { WeatherTable.cityId.eq(cityId) and DataLoadTable.loadTime.lessEq(DateTime(later24hours))}
                .maxBy { DataLoadTable.loadTime }.toWeatherDataLoad()

        }

        throw Exception("Something went wrong")
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

    private fun getWeatherLoadJoin() =
        Join(
            WeatherTable, DataLoadTable,
            onColumn = WeatherTable.loadId, otherColumn = DataLoadTable.id,
            joinType = JoinType.INNER)
}