import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

kotlinProject()

dependencies {
    implementation(project(":weather-commons"))
    implementation(project(":open-weather-integration"))
}