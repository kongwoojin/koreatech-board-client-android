// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(Dependencies.Google.ossLicensePlugin)
    }
}

plugins {
    id("com.android.application") version Versions.Android.application apply false
    id("com.android.library") version Versions.Android.library apply false
    id("org.jetbrains.kotlin.android") version Versions.Kotlin.kotlin apply false
    id("com.google.devtools.ksp") version Versions.ksp apply false
    id("com.google.dagger.hilt.android") version Versions.Hilt.hilt apply false
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}