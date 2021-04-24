plugins {
    `java-platform`
}

dependencies {
    constraints {
        api("androidx.activity:activity-ktx:1.2.2")
        api("androidx.appcompat:appcompat:1.2.0")
        api("androidx.constraintlayout:constraintlayout:2.0.4")
        api("androidx.core:core-ktx:1.3.2")
        api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
        api("com.facebook.shimmer:shimmer:0.5.0")
        api("com.google.android.material:material:1.3.0")
        api("io.insert-koin:koin-android:3.0.1-beta-2")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
        api("com.github.PhilJay:MPAndroidChart:v3.1.0")
    }
}
