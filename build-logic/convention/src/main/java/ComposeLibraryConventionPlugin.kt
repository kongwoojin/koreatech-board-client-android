import com.android.build.api.dsl.LibraryExtension
import com.kongjak.convention.configureKotlinCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure


internal class ComposeLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.kapt")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinCompose(this)
            }
        }
    }
}
