package com.shapegames.producer

import com.shapegames.response.OpenWeatherV25City
import com.shapegames.response.OpenWeatherV25Response
import com.shapegames.response.OpenWeatherV25Temperature
import com.shapegames.response.OpenWeatherV25TemperatureData
import com.shapegames.services.OpenWeatherV25Service
import com.shapegames.utils.fromUnixDateToJavaDate
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*

const val CITY_ID=99
const val TEMPERATURE = 25.0f
val TIME = Date().time

class OpenWeatherDataProducerTest {

    private val openWeatherV25Service = mockk<OpenWeatherV25Service>()
    private val openWeatherDataProducer = OpenWeatherDataProducer(openWeatherV25Service)

    @Test
    fun `will successfully return`() {
        every { openWeatherV25Service.getWeatherData(CITY_ID) } answers {getOpenWeatherDataResponse()}

        assertDoesNotThrow {
            val result = openWeatherDataProducer.getWeather(CITY_ID)

            Assertions.assertNotNull(result)
            Assertions.assertEquals(CITY_ID,result.cityId)
            Assertions.assertEquals(1, result.weatherData.size)
            Assertions.assertEquals(TEMPERATURE,result.weatherData[0].temperature)
            Assertions.assertEquals(TIME.fromUnixDateToJavaDate(),result.weatherData[0].forecastTime.time)
        }
    }

    private fun getOpenWeatherDataResponse():OpenWeatherV25Response{

        val v25Temp =  OpenWeatherV25Temperature(TEMPERATURE)
        val zagreb = OpenWeatherV25City(CITY_ID, "Zagreb", "Croatia")
        val temperatureData = OpenWeatherV25TemperatureData(TIME, v25Temp)

        return OpenWeatherV25Response(zagreb, listOf(temperatureData))
    }
}