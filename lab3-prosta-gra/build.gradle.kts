plugins {
    kotlin("jvm") version "2.0.21"
}

group = "pl.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.13")
}

tasks.test {
    useJUnitPlatform()
}