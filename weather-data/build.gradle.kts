plugins {
    kotlin("jvm") version "1.7.21"
    application
}

kotlinProject()

dependencies {
    implementation(project(":weather-commons"))

    implementation("org.jetbrains.exposed:exposed:0.17.14")
    implementation("org.xerial:sqlite-jdbc:3.40.0.0")
}