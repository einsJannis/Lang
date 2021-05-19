plugins {
	kotlin("jvm")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(fileTree("${project.rootDir}/lib/"))
	implementation("com.google.guava:guava:30.0-jre")
	implementation("net.java.dev.jna:jna:5.6.0")
}
