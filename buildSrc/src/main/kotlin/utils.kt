
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

const val junitVersion = "5.9.1"

/**
 * Configures the current project as a Kotlin project by adding the Kotlin `stdlib` as a dependency.
 */
fun Project.kotlinProject() {
    dependencies {
        // Kotlin libs
        "implementation"(kotlin("stdlib"))

        // Logging
        "implementation"("org.slf4j:slf4j-simple:1.7.36")
        "implementation"("io.github.microutils:kotlin-logging:1.7.8")

        // JSON handling
        "implementation"("com.beust:klaxon:5.6")

        // Mockk
        "testImplementation"("io.mockk:mockk:1.13.2")

        // JUnit 5
        "testImplementation"("org.junit.jupiter:junit-jupiter-api:$junitVersion")
        "testImplementation"("org.junit.jupiter:junit-jupiter-params:$junitVersion")
        "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:$junitVersion")


    }
}
