plugins {
    kotlin("jvm") version "1.7.21"
    application
}

kotlinProject()

dependencies {
    implementation(project(":weather-connection"))
}