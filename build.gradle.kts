plugins {
	java
	`java-library`
	id("com.diffplug.spotless") version "6.1.2"
}

group = "de.bluecolored.bluemap.api"

val apiVersion: String by project
version = apiVersion

val javaTarget = 8
java {
	sourceCompatibility = JavaVersion.toVersion(javaTarget)
	targetCompatibility = JavaVersion.toVersion(javaTarget)
}

repositories {
	mavenCentral()
}

dependencies {
   api ("com.flowpowered:flow-math:1.0.3")
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
		this as StandardJavadocDocletOptions

		links(
			"https://docs.oracle.com/javase/8/docs/api/",
			"https://javadoc.io/doc/com.flowpowered/flow-math/1.0.3/"
		)
	}
}
