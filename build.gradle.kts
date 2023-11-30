plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform(libs.kotest.bom))
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.runner)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("aoc2023.AppKt")
}

tasks.test {
    useJUnitPlatform()
}
