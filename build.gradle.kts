import java.io.IOException
import java.util.concurrent.TimeoutException

plugins {
    java
    `java-library`
    `maven-publish`
    id("com.diffplug.spotless") version "6.1.2"
}

fun String.runCommand(): String = ProcessBuilder(split("\\s(?=(?:[^'\"`]*(['\"`])[^'\"`]*\\1)*[^'\"`]*$)".toRegex()))
    .directory(projectDir)
    .redirectOutput(ProcessBuilder.Redirect.PIPE)
    .redirectError(ProcessBuilder.Redirect.PIPE)
    .start()
    .apply {
        if (!waitFor(10, TimeUnit.SECONDS)) {
            throw TimeoutException("Failed to execute command: '" + this@runCommand + "'")
        }
    }
    .run {
        val error = errorStream.bufferedReader().readText().trim()
        if (error.isNotEmpty()) {
            throw IOException(error)
        }
        inputStream.bufferedReader().readText().trim()
    }

val gitHash = "git rev-parse --verify HEAD".runCommand()
val clean = "git status --porcelain".runCommand().isEmpty()
val lastTag = "git describe --tags --abbrev=0".runCommand()
val lastVersion = lastTag.substring(1) // remove the leading 'v'
val commits = "git rev-list --count $lastTag..HEAD".runCommand()
println("Git hash: $gitHash" + if (clean) "" else " (dirty)")

group = "de.bluecolored.bluemap"
version = lastVersion +
        (if (commits == "0") "" else "-$commits") +
        (if (clean) "" else "-dirty")

println("Version: $version")

val javaTarget = 16
java {
    sourceCompatibility = JavaVersion.toVersion(javaTarget)
    targetCompatibility = JavaVersion.toVersion(javaTarget)

    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    api ("com.flowpowered:flow-math:1.0.3")
    api ("com.google.code.gson:gson:2.8.0")

    compileOnly ("org.jetbrains:annotations:23.0.0")
}

spotless {
    java {
        target ("src/*/java/**/*.java")

        licenseHeaderFile("LICENSE_HEADER")
        indentWithSpaces()
        trimTrailingWhitespace()
    }
}

tasks.withType(JavaCompile::class).configureEach {
    options.apply {
        encoding = "utf-8"
    }
}

tasks.withType(AbstractArchiveTask::class).configureEach {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}

tasks.javadoc {
    options {
        (this as? StandardJavadocDocletOptions)?.apply {
            links(
                "https://docs.oracle.com/javase/8/docs/api/",
                "https://javadoc.io/doc/com.flowpowered/flow-math/1.0.3/",
                "https://javadoc.io/doc/com.google.code.gson/gson/2.8.0/",
            )
            addStringOption("Xdoclint:none", "-quiet")
            if (JavaVersion.current().isJava9Compatible)
                addBooleanOption("html5", true)
        }
    }
}

tasks.processResources {
    from("src/main/resources") {
        include("de/bluecolored/bluemap/api/version.json")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        expand (
            "version" to project.version,
            "gitHash" to gitHash + if (clean) "" else " (dirty)"
        )
    }
}

publishing {
    repositories {
        maven {
            name = "bluecolored"

            val releasesRepoUrl = "https://repo.bluecolored.de/releases"
            val snapshotsRepoUrl = "https://repo.bluecolored.de/snapshots"
            url = uri(if (version == lastVersion) releasesRepoUrl else snapshotsRepoUrl)

            credentials {
                username = project.findProperty("bluecoloredUsername") as String? ?: System.getenv("BLUECOLORED_USERNAME")
                password = project.findProperty("bluecoloredPassword") as String? ?: System.getenv("BLUECOLORED_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
        }
    }
}
