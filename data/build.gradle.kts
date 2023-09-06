plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    kotlin("kapt")
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 25
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-debug.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    namespace = "com.kongjak.koreatechboard.data"
}

dependencies {

    implementation(project(":domain"))

    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.datastorePreference)
    implementation(Dependencies.AndroidX.pagingCommon)

    implementation(Dependencies.Etc.okhttp3)
    implementation(Dependencies.Etc.okhttp3Logging)
    implementation(Dependencies.Etc.retrofit2)
    implementation(Dependencies.Etc.retrofit2Gson)

    implementation(Dependencies.Google.hilt)

    implementation(Dependencies.Kotlin.kotlinCoroutineAndroid)
    implementation(Dependencies.Kotlin.kotlinCoroutineCore)

    kapt(Dependencies.Google.hiltCompiler)

    testImplementation(Dependencies.Test.junit)

    androidTestImplementation(Dependencies.AndroidTest.espresso)
    androidTestImplementation(Dependencies.AndroidTest.junit)
}
