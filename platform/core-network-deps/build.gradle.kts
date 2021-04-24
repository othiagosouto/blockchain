plugins {
    `java-platform`
}

dependencies {
    constraints {
        api("com.squareup.retrofit2:retrofit:2.9.0")
        api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
        api("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
        api("com.squareup.okhttp3:okhttp:4.9.1")
    }
}
