plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.azuyamat.mccollector"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}