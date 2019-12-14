import org.jetbrains.kotlin.config.KotlinCompilerVersion
import java.io.FileInputStream
import java.util.Properties

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
        forEach { it.buildConfigField("String", "TMDB_API_TOKEN", getLocalProperty("tmdb_api_token")) }
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
    implementation("com.google.android.material:material:1.2.0-alpha02")
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
