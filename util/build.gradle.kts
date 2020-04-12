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
    implementation("androidx.fragment:fragment-ktx:1.2.4")

    implementation("io.arrow-kt:arrow-core:${Version.arrowVersion}")
    implementation("io.arrow-kt:arrow-syntax:${Version.arrowVersion}")
    implementation("io.arrow-kt:arrow-meta:${Version.arrowVersion}")
}
