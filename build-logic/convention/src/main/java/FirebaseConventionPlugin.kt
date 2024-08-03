import org.gradle.api.Plugin
import org.gradle.api.Project

internal class FirebaseConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.firebase.crashlytics")
            }
        }
    }
}
