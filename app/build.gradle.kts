import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(28)

    defaultConfig {
        applicationId = "com.lukaszkalnik.moovis"
        minSdkVersion(23)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "0.1"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets.getByName("main").java.srcDirs("src/main/kotlin")
}

dependencies {
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))
    implementation("androidx.appcompat:appcompat:1.1.0")
}
