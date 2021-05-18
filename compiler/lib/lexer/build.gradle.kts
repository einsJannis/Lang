import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(project(":shared"))
}

tasks {

	withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = "1.8"
			optIn("kotlin.contracts.ExperimentalContracts")
			optIn("kotlin.ExperimentalStdlibApi")
		}
	}

}

fun org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions.optIn(annotation: String) {
	freeCompilerArgs = freeCompilerArgs + "-Xopt-in=$annotation"
}
