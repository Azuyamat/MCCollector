import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.23"
    `maven-publish`
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.azuyamat.mccollector"
version = "1.1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")

    /* Test */
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    /* Test */
    testImplementation(kotlin("test"))
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
    testImplementation("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    testImplementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks {
    register("jarTest", ShadowJar::class) {
        archiveClassifier.set("test")
        from(sourceSets["main"].output)
        from(sourceSets["test"].output)
        configurations = listOf(project.configurations.getByName("testRuntimeClasspath"))
    }
}