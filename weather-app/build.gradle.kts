
plugins {
    kotlin("jvm") version "1.7.21"
    application
}

kotlinProject()
dataLibs()

application {
    mainClass.set("WeatherApp")
}

dependencies {
    implementation(project(":weather-rest-api"))
    implementation(project(":weather-core"))
    implementation(project(":weather-commons"))
    implementation(project(":weather-data"))
    implementation(project(":open-weather-integration"))
}