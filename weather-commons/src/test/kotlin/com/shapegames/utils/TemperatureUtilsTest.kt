package com.shapegames.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


const val CELSIUS = 25.0f
const val FAHRENHEIT = 77.0f

class TemperatureUtilsTest {

    @Test
    fun `will successfully convert celsius to fahrenheit`() {
        val fahrenheit = celsiusToFahrenheit(CELSIUS)
        Assertions.assertEquals(FAHRENHEIT,fahrenheit)
    }

    @Test
    fun `will successfully convert fahrenheit to celsius`() {
        val celsius = fahrenheitToCelsius(FAHRENHEIT)
        Assertions.assertEquals(CELSIUS,celsius)
    }
}