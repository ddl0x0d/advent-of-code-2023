plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jsoup)
    implementation(libs.skarepit.fetcher)
    implementation(libs.skarepit.parser)
    testImplementation(platform(libs.kotest.bom))
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.datatest)
    testImplementation(libs.kotest.runner)
    testImplementation(libs.wiremock.kotlin)
    testImplementation(libs.wiremock.standalone)
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
