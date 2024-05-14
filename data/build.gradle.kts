@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.koreatechboard.library)
    alias(libs.plugins.koreatechboard.firebase)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.serialization)
    kotlin("kapt")
}

android {
    defaultConfig {
        targetSdk = 34

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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-debug.pro"
            )
        }
    }
    namespace = "com.kongjak.koreatechboard.data"
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    kapt(libs.androidx.room.compiler)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.negotiation)
    implementation(libs.ktor.kotlinx.serialization)
    implementation(libs.kotlinx.serialization)

    implementation(libs.koin.core)

    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)

    testImplementation(libs.junit)

    androidTestImplementation(libs.esspresso)
    androidTestImplementation(libs.junit.test)
}
