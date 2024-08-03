import com.android.build.api.dsl.ApplicationExtension
import com.kongjak.convention.configureKotlinCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal class ComposeApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.kapt")
                apply("org.jetbrains.kotlin.multiplatform")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinCompose(this)
            }
        }
    }
}
