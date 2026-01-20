plugins {
    id("java")
}

group = "ru.solovily"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-cli:commons-cli:1.11.0")
}