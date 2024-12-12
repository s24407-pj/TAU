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
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("org.mockito:mockito-core:5.14.2")

}

tasks.test {
    useJUnitPlatform()
}