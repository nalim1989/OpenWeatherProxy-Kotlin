
plugins {
    kotlin("jvm") version "1.7.21"
    application
}

kotlinProject()

dependencies {
    implementation(project(":weather-core"))
    implementation(project(":weather-commons"))

    implementation("io.javalin:javalin:5.2.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.1")
}

