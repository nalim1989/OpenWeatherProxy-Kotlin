package com.shapegames.validation

import com.shapegames.exceptions.ValidationException
import com.shapegames.model.TemperatureUnits
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class DataValidationTest {

    @Test
    fun `will validate integer param`() {
        assertDoesNotThrow {
            val value = validateIntegerParam("1", "validInt")
            Assertions.assertEquals(1,value)
        }
    }

    @Test
    fun `will not validate wrong integer param`() {
        assertThrows<ValidationException>() {
            validateIntegerParam("VV", "validInt")
        }
    }

    @Test
    fun `will validate float param`() {
        assertDoesNotThrow {
            val value = validateFloatParam("1.0", "validFloat")
            Assertions.assertEquals(1.0f,value)
        }
    }

    @Test
    fun `will not validate wrong float param`() {
        assertThrows<ValidationException> {
            validateFloatParam("VV", "validFloat")
        }
    }

    @Test
    fun `will validate integer list param`() {
        assertDoesNotThrow {
            val value = validateIntegerListParam(listOf("1","2"), "validateIntegerList")
            Assertions.assertEquals(2,value.size)
            Assertions.assertEquals(1, value[0])
            Assertions.assertEquals(2, value[1])
        }
    }

    @Test
    fun `will not validate wrong integer list param`() {
        assertThrows<ValidationException> {
            validateIntegerListParam(listOf("VV","BB"), "validateIntegerList")
        }
    }

    @Test
    fun `will validate celsius temperature units param`() {
        assertDoesNotThrow {
            val value = validateTemperatureUnitsParam("celsius", "temperatureUnit")
            Assertions.assertEquals(TemperatureUnits.CELSIUS,value)
        }
    }

    @Test
    fun `will validate fahrenheit temperature units param`() {
        assertDoesNotThrow {
            val value = validateTemperatureUnitsParam("fahrenheit", "temperatureUnit")
            Assertions.assertEquals(TemperatureUnits.FAHRENHEIT,value)
        }
    }

    @Test
    fun `will not validate wrong temperature units param`() {
        assertThrows<ValidationException> {
            validateTemperatureUnitsParam("BB", "temperatureUnit")
        }
    }
}