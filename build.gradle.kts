plugins {
    kotlin("jvm") version "2.1.10"
    id("com.diffplug.spotless") version "6.7.2"
}

group = "com.nschmidtg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito.kotlin:mockito-kotlin:6.0.0")
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target("src/**/*.kt")
        ktfmt().googleStyle().configure {
            it.setMaxWidth(120)
            it.setBlockIndent(4)
            it.setContinuationIndent(4)
            it.setRemoveUnusedImport(true)
        }
    }
}