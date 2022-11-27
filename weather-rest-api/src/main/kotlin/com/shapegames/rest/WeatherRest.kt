package com.shapegames.rest

import com.shapegames.exceptions.ValidationException
import com.shapegames.handler.WeatherRestHandler
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class WeatherRest(
    private val weatherRestHandler: WeatherRestHandler
): Runnable {

    override fun run() {
        app.start(6655)
    }

    // Set up Javalin rest app
    private val app = Javalin
        .create()
        .apply {
            exception(ValidationException::class.java) { e, ctx ->
                logger.error(e) { "Validation exception" }
                ctx.status(400)
            }
            // Unexpected exception: return HTTP 500
            exception(Exception::class.java) { e, ctx ->
                logger.error(e) { "Internal server error" }
                ctx.status(500)
            }
            // On 404: return message
            error(404) { ctx -> ctx.json("not found") }
        }

    init {
        // Set up URL endpoints for the rest app
        app.routes {
            get("/") {
                it.result("Welcome to Weather app!")
            }
            path("rest") {
                // Route to check whether the app is running
                // URL: /rest/health
                get("health") {
                    it.json("ok")
                }

                // V1
                path("v1") {
                    path("weather") {
                        path("summary") {
                            // URL: /rest/v1/weather/summary
                            get {
                                val cityIds = it.queryParams("locations")
                                val temperature = it.queryParam("temperature")
                                val unit = it.queryParam("unit")

                                val response=weatherRestHandler.handleTemperatureSummaryRequest(cityIds,temperature,unit)
                                it.json(response)
                            }
                        }
                        path("locations/{cityId}") {
                            // URL: /rest/v1/weather/locations/{cityId}
                            get {
                                val cityId = it.pathParam("cityId")

                                val response = weatherRestHandler.handleWeatherRequest(cityId)
                                it.json(response)
                            }
                        }
                    }
                }
            }
        }
    }
}