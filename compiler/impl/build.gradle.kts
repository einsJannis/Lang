plugins {
	kotlin("jvm")
}

repositories {
	mavenCentral()
	maven {
		setUrl("https://jitpack.io")
		mavenContent {
			includeGroupByRegex("com\\.github\\..*")
		}
	}
}

dependencies {
 	implementation(fileTree("${project.rootDir}/lib/"))
	implementation("com.google.guava:guava:30.0-jre")
	implementation("net.java.dev.jna:jna:5.6.0")
}
