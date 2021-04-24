import configs.KotlinConfig

plugins {
    id("java-library")
    id("kotlin")
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
    implementation("io.insert-koin:koin-core")

    testImplementation(platform(project(":platform:core-test-deps")))
    testImplementation("junit:junit")
    testImplementation("com.google.truth:truth")
    testImplementation("io.insert-koin:koin-test")
    testImplementation("io.mockk:mockk")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")

}