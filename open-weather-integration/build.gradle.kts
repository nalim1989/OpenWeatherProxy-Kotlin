plugins {
    kotlin("jvm") version "1.7.21"
}

kotlinProject()

dependencies {
    implementation(project(":weather-connection"))
    implementation(project(":weather-commons"))
}