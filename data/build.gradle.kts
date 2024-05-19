@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.koreatechboard.firebase)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.serialization)
    id("app.cash.sqldelight") version "2.0.2"
    kotlin("kapt")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    jvm("desktop")

    sourceSets {
        androidMain.dependencies {

            implementation(libs.paging.androidx.compose)
        }

        commonMain.dependencies {
            api(project(":domain"))

            api(libs.compose.webview.multiplatform)

            implementation(libs.coroutine.core)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.serialization)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.negotiation)
            implementation(libs.ktor.kotlinx.serialization)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.coroutines)
            implementation(libs.paging.common)
            implementation(libs.sqldelight.coroutines.extensions)


            implementation("com.benasher44:uuid:0.8.4")
        }
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    namespace = "com.kongjak.koreatechboard.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

dependencies {
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.kongjak.koreatechboard.data")
        }
    }
}
