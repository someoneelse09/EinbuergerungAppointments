plugins {
    kotlin("jvm") version "1.9.21"
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
    implementation("org.seleniumhq.selenium:selenium-java:4.16.1")
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.1.0")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")


    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Logback Classic (SLF4J implementation)
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}