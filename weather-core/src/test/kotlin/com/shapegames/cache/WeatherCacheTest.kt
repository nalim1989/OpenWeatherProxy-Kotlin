package com.shapegames.cache

import com.shapegames.model.WeatherData
import com.shapegames.services.WeatherDataLoader
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*



class WeatherCacheTest {
    private val CITY_ID=99
    private val TEMPERATURE = 25.0f
    private val TIME = Date()

    private val dataLoader = mockk<WeatherDataLoader>()
    private val weatherCache = WeatherCache(dataLoader)

    @Test
    fun `will successfully load from loader`() {
        every { dataLoader.loadWeatherData(CITY_ID) } answers {getWeatherData()}

        assertDoesNotThrow {
            var data:List<WeatherData>
            runBlocking {
                data= weatherCache.getWeatherData(CITY_ID)
            }
            Assertions.assertNotNull(data)
            Assertions.assertEquals(1, data.size)
            Assertions.assertEquals(TEMPERATURE,data[0].temperature)
            Assertions.assertEquals(TIME,data[0].forecastTime)
        }
        verify {
            dataLoader.loadWeatherData(CITY_ID)
        }
    }

    @Test
    fun `will successfully load from cache`() {
        every { dataLoader.loadWeatherData(CITY_ID) } answers {getWeatherData()}

        assertDoesNotThrow {
            var data:List<WeatherData>
            runBlocking {
                weatherCache.getWeatherData(CITY_ID) //First call
                data= weatherCache.getWeatherData(CITY_ID) // Second call
            }
            Assertions.assertNotNull(data)
            Assertions.assertEquals(1, data.size)
            Assertions.assertEquals(TEMPERATURE,data[0].temperature)
            Assertions.assertEquals(TIME,data[0].forecastTime)
        }

        // Data is loaded just once
        verify(exactly = 1)  {
            dataLoader.loadWeatherData(CITY_ID)
        }
    }

    private fun getWeatherData():List<WeatherData>{
        val weatherData = WeatherData(TEMPERATURE, TIME)
        return listOf(weatherData)
    }
}