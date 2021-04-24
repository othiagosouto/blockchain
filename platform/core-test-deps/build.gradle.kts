plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {

    val jUnitVersion = "4.13.2"
    val mockkVersion = "1.10.3-jdk8"
    val truthVersion = "1.1.2"
    val okhttpVersion = "4.9.1"
    val archUnitVersion = "0.17.0"

    constraints {
        api("junit:junit:$jUnitVersion")
        api("com.google.truth:truth:$truthVersion")
        api("com.squareup.okhttp3:mockwebserver:$okhttpVersion")
        api("io.mockk:mockk:$mockkVersion")
        api("io.mockk:mockk-android:$mockkVersion")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3")
        api("com.tngtech.archunit:archunit-junit4:$archUnitVersion")
    }
}
