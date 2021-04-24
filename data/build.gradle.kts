import configs.KotlinConfig

plugins {
    id("java-library")
    id("kotlin")
    kotlin("plugin.serialization") version "1.4.32"
}

java {
    sourceCompatibility = KotlinConfig.currentJavaVersion
    targetCompatibility = KotlinConfig.currentJavaVersion
}

sourceSets.getByName("main") {
    java.srcDir(KotlinConfig.mainSourceDir)
}

sourceSets.getByName("test") {
    java.srcDir(KotlinConfig.testSourceDir)
}

dependencies {
    implementation(project(":domain"))

    implementation(platform(project(":platform:core-kotlin-deps")))
    implementation("io.insert-koin:koin-core")

    testImplementation(platform(project(":platform:core-test-deps")))
    testImplementation("io.mockk:mockk")
    testImplementation("com.google.truth:truth")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
}
