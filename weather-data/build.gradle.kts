plugins {
    kotlin("jvm") version "1.7.21"
    application
}

kotlinProject()
dataLibs()

dependencies {
    implementation(project(":weather-commons"))
}