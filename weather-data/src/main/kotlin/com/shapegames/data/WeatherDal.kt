package com.shapegames.data

import com.shapegames.constants.DATA_EXPIRATION
import com.shapegames.model.CityWeatherData
import com.shapegames.model.WeatherData
import com.shapegames.model.WeatherDataLoad
import com.shapegames.utils.getTimeBefore
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.Date

class WeatherDal(private val db: Database) {

    fun fetchLatestWeather(cityId: Int): CityWeatherData? {
        var result:CityWeatherData?=null

        transaction(db) {
            val latestLoad = fetchLatestLoad(cityId)
            latestLoad?.let {
                val weatherData = WeatherTable
                    .select { WeatherTable.loadId.eq(latestLoad.id) }
                    .toList().toWeatherData()

                result = CityWeatherData(cityId, weatherData, latestLoad.loadTime)
            }
        }

         return result
    }

    fun fetchLatestLoad(cityId: Int): WeatherDataLoad? {
        val weatherJoin = getWeatherLoadJoin()

        var result:WeatherDataLoad?=null
        transaction(db) {

            result = weatherJoin
                .slice(DataLoadTable.loadTime)
                .select { WeatherTable.cityId.eq(cityId)}
                .maxByOrNull { DataLoadTable.loadTime }?.toWeatherDataLoad()


        }

        return result
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

    fun deleteInvalid(){
        val depreciationTime = getTimeBefore(Date(), DATA_EXPIRATION)
        transaction(db) {
            val invalidLoads = DataLoadTable
                .slice(DataLoadTable.id)
                .select(DataLoadTable.loadTime.less(DateTime(depreciationTime)))
                .toList().map { it[DataLoadTable.id] }.toList()

            DataLoadTable.deleteWhere { DataLoadTable.id.inList(invalidLoads) }
            WeatherTable.deleteWhere { WeatherTable.loadId.inList(invalidLoads) }
        }
    }

    private fun getWeatherLoadJoin() =
        Join(
            WeatherTable, DataLoadTable,
            onColumn = WeatherTable.loadId, otherColumn = DataLoadTable.id,
            joinType = JoinType.INNER)
}
