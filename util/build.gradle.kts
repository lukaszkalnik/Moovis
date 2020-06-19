plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(29)
    }

    sourceSets.getByName("main").java.srcDirs("src/main/kotlin")

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycleVersion}")
    implementation("androidx.fragment:fragment-ktx:1.3.0-alpha06")
    implementation("androidx.recyclerview:recyclerview:1.2.0-alpha03")

    implementation("io.arrow-kt:arrow-core:${Version.arrowVersion}")
    implementation("io.arrow-kt:arrow-syntax:${Version.arrowVersion}")
    implementation("io.arrow-kt:arrow-meta:${Version.arrowVersion}")
}
