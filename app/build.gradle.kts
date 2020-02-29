import org.jetbrains.kotlin.config.KotlinCompilerVersion
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("plugin.serialization") version "1.3.61"
}

android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId = "com.lukaszkalnik.moovis"
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "0.1"
    }

    buildTypes {
        forEach { it.buildConfigField("String", "TMDB_API_KEY", getLocalProperty("tmdb_api_key")) }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets.getByName("main").java.srcDirs("src/main/kotlin")
    sourceSets.getByName("test").java.srcDirs("src/test/kotlin")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutinesVersion}")

    implementation(project(":runtime-configuration"))

    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("com.google.android.material:material:1.2.0-alpha05")

    val lifecycleVersion = "2.2.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    implementation("com.github.bumptech.glide:glide:4.10.0")

    implementation("com.squareup.retrofit2:retrofit:2.7.1")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.4.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.4.0")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.assertj:assertj-core:3.14.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutinesVersion}")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
}

fun getLocalProperty(name: String): String {
    val fileName = "local.properties"
    val propertiesFile = rootProject.file(fileName)

    if (!propertiesFile.exists()) {
        print("$fileName does not exist")
        return ""
    }

    with(Properties()) {
        load(FileInputStream(propertiesFile))
        val property = get(name)
        return if (property == null) {
            print("Property $name not found in $fileName")
            ""
        } else {
            property as String
        }
    }
}
