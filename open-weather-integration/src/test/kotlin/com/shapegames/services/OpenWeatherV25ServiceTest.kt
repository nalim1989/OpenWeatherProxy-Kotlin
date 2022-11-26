package com.shapegames.services

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class OpenWeatherV25ServiceTest {

    private val openWeatherV25Service = OpenWeatherV25Service()

    @Test
    fun `will successfully return`() {
        assertDoesNotThrow {
            openWeatherV25Service.getWeatherData(2172797)
        }
    }

}