@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.koreatechboard.library)
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
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.napier)
            implementation(libs.paging.common)
            implementation(libs.uuid)
        }
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

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
    namespace = "com.kongjak.koreatechboard.domain"
}

dependencies {
}
