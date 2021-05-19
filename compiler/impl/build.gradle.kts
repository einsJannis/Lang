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
	implementation(project(":shared"))
	implementation(project(":lexerLib"))
	implementation(project(":parserLib"))
	implementation(project(":llvm"))
}
