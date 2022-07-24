plugins {
    java
    `java-library`
    id("com.diffplug.spotless") version "6.1.2"
    id ("com.palantir.git-version") version "0.12.3"
}

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
val git = versionDetails()

group = "de.bluecolored.bluemap.api"

val apiVersion: String by project
version = apiVersion

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
            "gitHash" to git.gitHashFull,
            "gitClean" to git.isCleanTag
        )
    }
}
