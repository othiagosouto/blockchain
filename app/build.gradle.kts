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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.32")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
