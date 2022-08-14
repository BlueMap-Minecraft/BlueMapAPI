import java.io.IOException

plugins {
    java
    `java-library`
    //`maven-publish`
    id("com.diffplug.spotless") version "6.1.2"
}

fun String.runCommand(): String = ProcessBuilder(split("\\s(?=(?:[^'\"`]*(['\"`])[^'\"`]*\\1)*[^'\"`]*$)".toRegex()))
    .directory(projectDir)
    .redirectOutput(ProcessBuilder.Redirect.PIPE)
    .redirectError(ProcessBuilder.Redirect.PIPE)
    .start()
    .apply { waitFor(60, TimeUnit.SECONDS) }
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

group = "de.bluecolored.bluemap.api"
version = lastVersion +
        (if (commits == "0") "" else "-$commits") +
        (if (clean) "" else "-dirty")

println("Version: $version")

val javaTarget = 11
java {
    sourceCompatibility = JavaVersion.toVersion(javaTarget)
    targetCompatibility = JavaVersion.toVersion(javaTarget)
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
                "https://docs.oracle.com/javase/8/docs/api/"
            )
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

/*
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/BlueMap-Minecraft/BlueMapAPI")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
            artifactId = "bluemap_api"
        }
    }
}
*/
