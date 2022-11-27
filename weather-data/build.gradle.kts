plugins {
    kotlin("jvm") version "1.7.21"
}

kotlinProject()
dataLibs()

dependencies {
    implementation(project(":weather-commons"))
}