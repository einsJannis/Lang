plugins {
	kotlin("jvm")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(project(":shared"))
	implementation(project(":lexerLib"))
}
