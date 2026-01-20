plugins {
    id("java")
    id("application")
}

group = "ru.solovily"
version = "1.0"

application {
    mainClass = "ru.solovily.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-cli:commons-cli:1.11.0")
}

tasks.jar {
    archiveBaseName.set("filefilter")
    archiveVersion.set("")      // ← removes version
    archiveClassifier.set("")   // ← removes classifier (like "plain")

    manifest {
        attributes["Main-Class"] = "ru.solovily.Main"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}