import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

val localStoreFile: String = gradleLocalProperties(rootDir).getProperty("localStoreFile")
val localStorePassword: String = gradleLocalProperties(rootDir).getProperty("localStorePassword")
val localKeyAlias: String = gradleLocalProperties(rootDir).getProperty("localKeyAlias")
val localKeyPassword: String = gradleLocalProperties(rootDir).getProperty("localKeyPassword")

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.koreatechboard.firebase)
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.ktlint)
    alias(libs.plugins.google.services)
    alias(libs.plugins.serialization)
    kotlin("plugin.parcelize")
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

    sourceSets {
        androidMain.dependencies {

            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.browser)
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.paging.compose)
            implementation(libs.androidx.paging.runtime)
            implementation(libs.androidx.webkit)

            implementation(libs.material)
            implementation(libs.oss.licenses)

            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.messaging)
            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.analytics)

            implementation(libs.accompanist.permissions)

            implementation(libs.koin.androidx.compose)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.navigation)
            implementation("androidx.compose.material:material-ripple:1.7.0-alpha05")
        }

        commonMain.dependencies {

            api(project(":data"))
            api(project(":domain"))

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.components.resources)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)

            implementation(libs.coil)
            implementation(libs.coil.compose)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.negotiation)
            implementation(libs.ktor.kotlinx.serialization)
            implementation(libs.kotlinx.serialization)

            implementation(libs.koin.core)

            implementation(libs.orbit.core)
            implementation(libs.orbit.viewmodel)
            implementation(libs.orbit.compose)

            implementation(libs.materialKolor)

            implementation(libs.ktxml)

            implementation(libs.sqldelight.coroutines.extensions)
            implementation (libs.sqldelight.android.driver)

            implementation("com.benasher44:uuid:0.8.4")
            implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.5.0")
        }
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.kongjak.koreatechboard"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.project.version.code.get().toInt()
        versionName = libs.versions.project.version.name.get().toString()

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(localStoreFile)
            storePassword = localStorePassword
            keyAlias = localKeyAlias
            keyPassword = localKeyPassword
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
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
    namespace = "com.kongjak.koreatechboard"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

dependencies {
}
