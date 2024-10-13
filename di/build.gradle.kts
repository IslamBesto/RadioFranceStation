plugins {
    id("com.android.library")
    kotlin("android")
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
    implementation(project(":data"))
}