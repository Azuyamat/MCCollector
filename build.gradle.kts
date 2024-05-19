plugins {
    `maven-publish`
    java
    kotlin("jvm") version "1.9.23"
}

group = "com.azuyamat.mccollector"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")

    testImplementation(kotlin("test"))
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.azuyamat.mccollector"
            artifactId = "mccollector"
            version = "1.0.1"

            from(components["java"])
        }
    }
}