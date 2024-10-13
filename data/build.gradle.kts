plugins {
    id("com.android.library")
    kotlin("android")
    alias(libs.plugins.apollo)
}

apollo {
    service("service") {
        schemaFile.set(file("src/main/com/example/schema.graphqls"))
        packageName.set("com.example")
    }
}


android {
    namespace = "com.example.radiofrance.data"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.apollo.runtime)

    implementation(project(":domain"))

    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.stdlib)
    testImplementation(libs.apollo.mockserver)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}