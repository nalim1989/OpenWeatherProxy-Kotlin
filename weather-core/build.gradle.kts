
plugins {
    kotlin("jvm") version "1.7.21"
}

kotlinProject()

dependencies {
    implementation(project(":weather-commons"))
    implementation(project(":weather-data"))
    implementation(project(":open-weather-integration"))

    implementation("io.github.reactivecircus.cache4k:cache4k:0.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}