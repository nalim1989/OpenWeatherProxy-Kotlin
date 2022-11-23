import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

kotlinProject()

application {
    mainClass.set("WeatherApp")
}

dependencies {
    implementation(project(":open-weather-integration"))
}