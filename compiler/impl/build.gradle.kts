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
tasks {

	withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions {
			optIn("kotlin.ExperimentalStdlibApi")
		}
	}

}

fun org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions.optIn(annotation: String) {
	freeCompilerArgs = freeCompilerArgs + "-Xopt-in=$annotation"
}
