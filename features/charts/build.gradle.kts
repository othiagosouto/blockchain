import configs.AndroidConfig
import configs.KotlinConfig

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(AndroidConfig.compileSdk)
    buildToolsVersion(AndroidConfig.buildToolsVersion)
    defaultConfig {
        targetSdkVersion(AndroidConfig.targetSdk)
        minSdkVersion(AndroidConfig.minSdk)

        testInstrumentationRunner = AndroidConfig.instrumentationTestRunner
    }

    buildTypes {

        getByName("debug") {
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = KotlinConfig.currentJavaVersion
        targetCompatibility = KotlinConfig.currentJavaVersion
    }

    kotlinOptions {
        jvmTarget = KotlinConfig.jsonTarget
        allWarningsAsErrors = true
    }

    sourceSets.getByName("main") {
        java.srcDir(KotlinConfig.mainSourceDir)
    }

    sourceSets.getByName("test") {
        java.srcDir(KotlinConfig.testSourceDir)
    }

    sourceSets.getByName("androidTest") {
        java.srcDir(AndroidConfig.androidTestSourceDir)
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(platform(project(":platform:core-kotlin-deps")))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation(platform(project(":platform:core-android-deps")))
    implementation("androidx.constraintlayout:constraintlayout")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")
    implementation("androidx.activity:activity-ktx")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:")
    implementation("androidx.core:core-ktx")
    implementation("androidx.appcompat:appcompat")
    implementation("com.google.android.material:material")
    implementation("io.insert-koin:koin-android")
    implementation("com.github.PhilJay:MPAndroidChart")
    implementation("com.facebook.shimmer:shimmer")

    testImplementation(platform(project(":platform:core-integration-test-deps")))
    testImplementation("androidx.test:core")
    testImplementation("org.robolectric:robolectric")
    testImplementation("androidx.test.ext:junit")
    testImplementation("androidx.test.espresso:espresso-core")

    testImplementation(platform(project(":platform:core-test-deps")))
    testImplementation("com.google.truth:truth")
    testImplementation("io.insert-koin:koin-test")
    testImplementation("io.mockk:mockk")
    testImplementation("app.cash.turbine:turbine")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")

    androidTestImplementation(platform(project(":platform:core-integration-test-deps")))
    androidTestImplementation("androidx.test:runner")
    androidTestImplementation("androidx.test:core")
    androidTestImplementation("androidx.test.ext:junit")
}
