import configs.AndroidConfig
import configs.KotlinConfig

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdkVersion(AndroidConfig.compileSdk)
    buildToolsVersion(AndroidConfig.buildToolsVersion)

    defaultConfig {
        targetSdkVersion(AndroidConfig.targetSdk)
        minSdkVersion(AndroidConfig.minSdk)

        applicationId = AndroidConfig.applicationId
        versionName = AndroidConfig.versionName
        versionCode = AndroidConfig.versionCode
        testInstrumentationRunner = AndroidConfig.instrumentationTestRunner
    }

    buildTypes {

        getByName("debug") {
            isTestCoverageEnabled = true
            buildConfigField("String", "SERVER_URL", "\"https://api.blockchain.info/\"")
        }

        getByName("release") {
            isMinifyEnabled = false
            buildConfigField("String", "SERVER_URL", "\"https://api.blockchain.info/\"")
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
    implementation(project(":data"))
    implementation(project(":remote"))
    implementation(project(":features:charts"))

    implementation(platform(project(":platform:core-android-deps")))
    implementation("androidx.activity:activity-ktx")
    implementation("com.google.android.material:material")
    implementation("io.insert-koin:koin-android")

    testImplementation(platform(project(":platform:core-integration-test-deps")))
    testImplementation("androidx.test:core")
    testImplementation("org.robolectric:robolectric")
    testImplementation("androidx.test.ext:junit")
    testImplementation("androidx.test.espresso:espresso-intents")

    testImplementation(platform(project(":platform:core-test-deps")))
    testImplementation("com.google.truth:truth")
    testImplementation("io.insert-koin:koin-test")
}
