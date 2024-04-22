import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Zip
import java.io.File

plugins {
    kotlin("jvm") version "1.9.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.19.1")
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.1.0")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

    // Logback Classic (SLF4J implementation)
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "org.example.MainKt"))
        }
    }
}


tasks {
    build {
        dependsOn(shadowJar)
    }
}

val libs by extra { File("../bin/lib.zip") }
val resources = "$projectDir/src/main/resources/"

tasks.register<Copy>("unzipLibs") {
    from(zipTree(libs))
    into(resources)
}

tasks.register<Zip>("zipLibs") {
    from(resources)
    archiveFileName.set("lib.zip")
    destinationDirectory.set(File("../bin"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
