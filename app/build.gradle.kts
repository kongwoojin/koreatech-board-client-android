import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

val localStoreFile: String = gradleLocalProperties(rootDir).getProperty("localStoreFile")
val localStorePassword: String = gradleLocalProperties(rootDir).getProperty("localStorePassword")
val localKeyAlias: String = gradleLocalProperties(rootDir).getProperty("localKeyAlias")
val localKeyPassword: String = gradleLocalProperties(rootDir).getProperty("localKeyPassword")

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.koreatechboard.application)
    alias(libs.plugins.koreatechboard.compose)
    alias(libs.plugins.koreatechboard.firebase)
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.ktlint)
    alias(libs.plugins.google.services)
    alias(libs.plugins.serialization)
    kotlin("plugin.parcelize")
    kotlin("kapt")
}

android {
    defaultConfig {
        applicationId = "com.kongjak.koreatechboard"
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
    namespace = "com.kongjak.koreatechboard"
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.webkit)
    implementation(libs.androidx.navigation.compose)
    kapt(libs.androidx.room.compiler)

    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.negotiation)
    implementation(libs.ktor.kotlinx.serialization)
    implementation(libs.kotlinx.serialization)

    implementation(libs.material)
    implementation(libs.oss.licenses)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    implementation(libs.accompanist.permissions)

    implementation(libs.orbit.core)
    implementation(libs.orbit.viewmodel)
    implementation(libs.orbit.compose)

    implementation(libs.materialKolor)

    implementation(libs.ktxml)

    implementation(libs.sqldelight.coroutines.extensions)
    implementation (libs.sqldelight.android.driver)

    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.navigation)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)

    androidTestImplementation(libs.compose.junit)
    androidTestImplementation(libs.esspresso)
    androidTestImplementation(libs.junit.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
}
