package com.shapegames.handler

import com.shapegames.services.WeatherService
import com.shapegames.utils.fahrenheitToCelsius
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

private const val CITY_ID = 99
private const val TEMPERATURE_FAHRENHEITS=300.0f
private const val TEMPERATURE_CELSIUS=25.0f
class WeatherRestHandlerTest {

    private val weatherService = mockk<WeatherService>()
    private val weatherRestHandler = WeatherRestHandler(weatherService)

    @Test
    fun `will handle fahrenheit correctly`() {
        every { weatherService.getFavouritesAboveTemp(any(),any()) } answers { setOf() }

        weatherRestHandler.handleTemperatureSummaryRequest(CITY_ID.toString(),TEMPERATURE_FAHRENHEITS.toString(), "FAHRENHEIT")

        verify {
            weatherService.getFavouritesAboveTemp(fahrenheitToCelsius(TEMPERATURE_FAHRENHEITS), setOf(CITY_ID))
        }
    }

    @Test
    fun `will handle celsius correctly`() {
        every { weatherService.getFavouritesAboveTemp(any(),any()) } answers { setOf() }

        weatherRestHandler.handleTemperatureSummaryRequest(CITY_ID.toString(),TEMPERATURE_CELSIUS.toString(), "CELSIUS")

        verify {
            weatherService.getFavouritesAboveTemp(TEMPERATURE_CELSIUS, setOf(CITY_ID))
        }
    }
}