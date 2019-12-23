import org.jetbrains.kotlin.config.KotlinCompilerVersion
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("kotlinx-serialization")
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
        forEach { it.buildConfigField("String", "TMDB_API_TOKEN", getLocalProperty("tmdb_api_token")) }
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
}

dependencies {
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")

    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("com.google.android.material:material:1.2.0-alpha03")

    implementation("com.github.bumptech.glide:glide:4.10.0")
    implementation("com.squareup.retrofit2:retrofit:2.7.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.4.0")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
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
