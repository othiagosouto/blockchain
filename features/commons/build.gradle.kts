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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(platform(project(":platform:core-android-deps")))
    implementation("androidx.core:core-ktx")
    implementation("androidx.appcompat:appcompat")
    implementation("com.google.android.material:material")

    testImplementation(platform(project(":platform:core-integration-test-deps")))
    testImplementation("androidx.test:core")
    testImplementation("org.robolectric:robolectric")
    testImplementation("androidx.test.ext:junit")
}
