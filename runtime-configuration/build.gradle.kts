plugins {
    kotlin("jvm")
}

sourceSets.getByName("main").java.srcDirs("src/main/kotlin")

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}