package configs

import org.gradle.api.JavaVersion

object KotlinConfig {
    const val jsonTarget = "1.8"
    val currentJavaVersion: JavaVersion = JavaVersion.VERSION_1_8
    const val detektPlugin: String = "io.gitlab.arturbosch.detekt"
    const val detektPluginConfiguration: String = "detektPlugins"
    const val detektFormatting =  "io.gitlab.arturbosch.detekt:detekt-formatting:1.16.0"
    const val mainSourceDir = "src/main/kotlin"
    const val testSourceDir = "src/test/kotlin"
}