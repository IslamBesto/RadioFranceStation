plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.koin.core)
    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.stdlib)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}