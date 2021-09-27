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

sourceSets {
	get("main").java.srcDir("src/main/generated")
}

tasks {

	withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions {
			optIn("kotlin.ExperimentalStdlibApi")
		}
	}

	register<dev.einsjannis.gradle.kotlin.compiler.parser.SequencePatternGeneratorTask>("sequencePatternGeneratorTask") {
		outputDirectory.set(file("src/main/generated"))
		amountOfSequencePatterns.set(16)
	}

}

fun org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions.optIn(annotation: String) {
	freeCompilerArgs = freeCompilerArgs + "-Xopt-in=$annotation"
}
