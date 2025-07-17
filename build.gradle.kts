plugins {
    kotlin("jvm") version libs.versions.kotlin
    `maven-publish`
    java
    alias(libs.plugins.shadow)
    alias(libs.plugins.runpaper)
}

group = "com.azuyamat.mccollector"
version = "1.3.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.fancyinnovations.com/releases")

    /* Test */
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.fancynpcs)

    /* Test */
    testImplementation(kotlin("test"))
    testCompileOnly(libs.paper)
    testImplementation(libs.acf)
    testCompileOnly(libs.fancynpcs)
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
            from(components["java"])
        }
    }
}

tasks {
    shadowJar {
        archiveClassifier.set("test")
        from(sourceSets["main"].output)
        from(sourceSets["test"].output)
        from(sourceSets["test"].resources)
        configurations = listOf(project.configurations.getByName("testRuntimeClasspath"))
    }

    runServer {
        minecraftVersion("1.21.4")
        jvmArgs("-DPaper.IgnoreJavaVersion=true", "-Dcom.mojang.eula.agree=true")

        downloadPlugins {
            modrinth("viaversion", "5.4.0-SNAPSHOT+747")
            modrinth("fancynpcs", libs.versions.fancynpcs.get())
        }
    }
}