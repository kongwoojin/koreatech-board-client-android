pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://jogamp.org/deployment/maven")
    }
}

rootProject.name = "Koreatech_Board"
include(":app")
include(":domain")
include(":data")

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
