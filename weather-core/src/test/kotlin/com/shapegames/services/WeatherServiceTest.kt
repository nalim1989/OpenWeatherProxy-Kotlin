package com.shapegames.services

import com.shapegames.model.WeatherData
import com.shapegames.provider.WeatherDataProvider
import com.shapegames.utils.get24HoursFromNowTime
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*

private const val CITY_ID=99
private const val TEMPERATURE = 25.0f
private val TIME = Date()
class WeatherServiceTest {

    private val dataProvider = mockk<WeatherDataProvider>()
    private val weatherService = WeatherService(dataProvider)

    @Test
    fun `will return favourites above`() {
        every { dataProvider.getNextDayWeather(CITY_ID) } answers {getNextDayWeatherData()}
        assertDoesNotThrow {
            val result = weatherService.getFavouritesAboveTemp(10.0f, setOf(CITY_ID))

            Assertions.assertEquals(1, result.size)
            Assertions.assertEquals(CITY_ID, result.first())
        }
    }

    @Test
    fun `will return favourites below`() {
        every { dataProvider.getNextDayWeather(CITY_ID) } answers {getNextDayWeatherData()}
        assertDoesNotThrow {
            val result = weatherService.getFavouritesAboveTemp(99.0f, setOf(CITY_ID))

            Assertions.assertEquals(0, result.size)
        }
    }

    private fun getNextDayWeatherData():List<WeatherData>{
        return  listOf(WeatherData(TEMPERATURE, get24HoursFromNowTime(TIME)))
    }
}