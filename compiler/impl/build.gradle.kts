plugins {
	kotlin("jvm")
}

dependencies {
	implementation(project(":shared"))
	implementation(project(":lexerLib"))
	implementation(project(":parserLib"))
	implementation(project(":llvm"))
}

repositories {
	mavenCentral()
}
