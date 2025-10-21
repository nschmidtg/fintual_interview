plugins {
    kotlin("jvm") version "2.1.10"
    id("com.diffplug.spotless") version "6.7.2"
    jacoco
}

group = "com.nschmidtg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// --- Integration Test Source Set ---
val intTest = sourceSets.create("intTest") {
    kotlin.srcDir("src/intTest/kotlin")
    resources.srcDir("src/intTest/resources")
    compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
    runtimeClasspath += output + compileClasspath
}

configurations {
    val intTestImplementation by getting
    val intTestRuntimeOnly by getting
}

val junitJupiterVersion = "5.10.0"
dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("org.junit.platform:junit-platform-launcher:1.10.0")
    testImplementation("org.junit.platform:junit-platform-engine:1.10.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:6.0.0")

    add("intTestImplementation", kotlin("test"))
    add("intTestImplementation", "org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    add("intTestImplementation", "org.junit.platform:junit-platform-launcher:1.10.0")
    add("intTestImplementation", "org.junit.platform:junit-platform-engine:1.10.0")
    add("intTestImplementation", "org.mockito.kotlin:mockito-kotlin:6.0.0")
    add("intTestImplementation", sourceSets["main"].output)
}

tasks.test {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.8".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestReport)
    dependsOn(tasks.jacocoTestCoverageVerification)
}

kotlin {
    jvmToolchain(21)
}


tasks.register<Test>("intTest") {
    description = "Runs integration tests."
    group = "verification"
    testClassesDirs = sourceSets["intTest"].output.classesDirs
    classpath = sourceSets["intTest"].runtimeClasspath
    useJUnitPlatform()
    shouldRunAfter(tasks.test)
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