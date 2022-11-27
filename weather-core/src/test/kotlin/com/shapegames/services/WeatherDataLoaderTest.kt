package com.shapegames.services

import com.shapegames.exceptions.DataLoadException
import com.shapegames.model.CityWeatherData
import com.shapegames.model.IWeatherDataProducer
import com.shapegames.model.WeatherData
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.*

private const val CITY_ID=99
private const val TEMPERATURE = 25.0f
private val TIME = Date()

class WeatherDataLoaderTest {

    private val dataProducer = mockk<IWeatherDataProducer>()
    private val dataProducers = setOf<IWeatherDataProducer>(dataProducer)
    private val dataLoader = WeatherDataLoader(dataProducers)

    @Test
    fun `will throw if invalid data`() {
        every { dataProducer.getWeather(CITY_ID) } answers {getInvalidCityWeatherData()}

        assertThrows<DataLoadException>() {
            dataLoader.loadWeatherData(CITY_ID)
        }
    }

    @Test
    fun `will throw if no data`() {
        every { dataProducer.getWeather(CITY_ID) } answers {nothing}

        assertThrows<DataLoadException>() {
            dataLoader.loadWeatherData(CITY_ID)
        }
    }

    @Test
    fun `will throw if deprecated`() {
        every { dataProducer.getWeather(CITY_ID) } answers {getDeprecatedCityWeatherData()}

        assertThrows<DataLoadException>() {
            dataLoader.loadWeatherData(CITY_ID)
        }
    }

    @Test
    fun `return if valid data`() {
        every { dataProducer.getWeather(CITY_ID) } answers {getValidCityWeatherData()}

        assertDoesNotThrow {
            dataLoader.loadWeatherData(CITY_ID)
        }
    }

    private fun getInvalidCityWeatherData():CityWeatherData{
        val weatherData = WeatherData(TEMPERATURE, TIME)
        return CityWeatherData(CITY_ID, listOf(weatherData), TIME)
    }

    private fun getValidCityWeatherData():CityWeatherData{
        val weatherData = WeatherData(TEMPERATURE, getAfter5Days())
        return CityWeatherData(CITY_ID, listOf(weatherData), TIME)
    }

    private fun getDeprecatedCityWeatherData():CityWeatherData{
        val weatherData = WeatherData(TEMPERATURE, TIME)
        return CityWeatherData(CITY_ID, listOf(weatherData), getDeprecatedTime())
    }

    private fun getAfter5Days():Date{
        val calendar: Calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 5
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        calendar.add(Calendar.DAY_OF_MONTH,5)

        return calendar.time
    }

    private fun getDeprecatedTime():Date{
        val calendar: Calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 5
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        calendar.add(Calendar.DAY_OF_MONTH,-5)

        return calendar.time
    }
}