package com.shapegames.services

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class OpenWeatherServiceTest {

    private val openWeatherService = OpenWeatherService()

    @Test
    fun `will successfully return`() {
        assertDoesNotThrow {
            openWeatherService.getWeatherData(2172797)
        }
    }

}