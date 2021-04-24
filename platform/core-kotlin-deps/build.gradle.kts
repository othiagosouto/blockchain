plugins {
    `java-platform`
}

dependencies {
    val koinVersion = "3.0.1-beta-2"
    constraints {
        api("org.jetbrains.kotlin:kotlin-stdlib:1.4.32")
        api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
        api("io.insert-koin:koin-core:$koinVersion")
        api("io.insert-koin:koin-test:$koinVersion")
        api("app.cash.turbine:turbine:0.4.1")
    }
}
