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
    implementation(platform(project(":platform:core-kotlin-deps")))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")
    implementation("io.insert-koin:koin-core")

    implementation(project(":domain"))
    implementation(platform(project(":platform:core-network-deps")))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("com.squareup.retrofit2:retrofit")
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter")

    testImplementation(platform(project(":platform:core-test-deps")))
    testImplementation("io.mockk:mockk")
    testImplementation("com.squareup.okhttp3:mockwebserver")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("io.insert-koin:koin-test")
    testImplementation("com.google.truth:truth")

}
