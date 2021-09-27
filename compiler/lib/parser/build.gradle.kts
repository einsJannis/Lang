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

	val sequencePatternGeneratorTask by register<dev.einsjannis.gradle.kotlin.compiler.parser.SequencePatternGeneratorTask>("sequencePatternGeneratorTask") {
		outputDirectory.set(file("src/main/generated"))
		amountOfSequencePatterns.set(16)
	}
	withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions {
			optIn("kotlin.ExperimentalStdlibApi")
		}
		dependsOn(sequencePatternGeneratorTask)
	}

	named("clean") {
		doLast {
			file("src/main/generated").deleteRecursively()
		}
	}
}

fun org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions.optIn(annotation: String) {
	freeCompilerArgs = freeCompilerArgs + "-Xopt-in=$annotation"
}
