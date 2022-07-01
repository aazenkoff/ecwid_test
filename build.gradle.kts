import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "com.ecwid"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation(kotlin("test"))
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "com.ecwid.MainKt")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Test> {
    minHeapSize = "512m"
    maxHeapSize = "1024m"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}