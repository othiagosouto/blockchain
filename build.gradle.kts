import configs.KotlinConfig

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
        classpath("com.facebook.testing.screenshot:plugin:0.13.0")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.16.0")
    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter {
            content {
                includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
            }
        }
        maven(url = "https://jitpack.io")
    }

    apply(plugin = KotlinConfig.detektPlugin)

    this.dependencies {
        add(KotlinConfig.detektPluginConfiguration, KotlinConfig.detektFormatting)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
