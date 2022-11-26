package com.shapegames.rest

import io.javalin.Javalin
import mu.KotlinLogging
import io.javalin.apibuilder.ApiBuilder.*

private val logger = KotlinLogging.logger {}

class WeatherRest: Runnable {

    override fun run() {
        app.start(6655)
    }

    // Set up Javalin rest app
    private val app = Javalin
        .create()
        .apply {
            // Unexpected exception: return HTTP 500
            exception(Exception::class.java) { e, _ ->
                logger.error(e) { "Internal server error" }
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
                                it.json("ok")
                            }
                        }
                        path("locations") {
                            // URL: /rest/v1/weather/summary/locations/
                            get {
                                it.json("ok")
                            }
                        }
                    }
                }
            }
        }
    }
}