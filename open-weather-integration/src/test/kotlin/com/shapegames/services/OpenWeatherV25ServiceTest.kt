package com.shapegames.services

import com.shapegames.constants.OPEN_WEATHER_RESPONSE_FILE
import com.shapegames.exceptions.OpenWeatherConnectionException
import com.shapegames.rest.RestConnection
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class OpenWeatherV25ServiceTest {

    private val mockedConnection = mockkObject(RestConnection)
    private val openWeatherV25Service = OpenWeatherV25Service()

    private val responseExampleJson = this::class.java.classLoader.getResource(OPEN_WEATHER_RESPONSE_FILE)?.readText()
    @Test
    fun `will successfully return`() {
        every { RestConnection.syncGet(any()) } answers {responseExampleJson }
        assertDoesNotThrow {
            val result = openWeatherV25Service.getWeatherData(2172797)
            assertNotNull(result)
        }
    }
    @Test
    fun `will throw in case of wrong result`() {
        every { RestConnection.syncGet(any()) } answers {nothing }
        assertThrows<OpenWeatherConnectionException>() {
            openWeatherV25Service.getWeatherData(2172797)
        }
    }
}