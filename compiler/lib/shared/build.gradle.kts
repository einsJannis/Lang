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

sourceSets {
	get("main").java.srcDir("src/main/generated")
}

tasks {
	val tupleGeneratorTask by register<dev.einsjannis.gradle.kotlin.TupleGeneratorTask>("tupleGeneratorTask") {
		outputDirectory.set(file("src/main/generated"))
		amountOfTuples.set(16)
	}
	withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions {
			optIn("kotlin.contracts.ExperimentalContracts")
			optIn("kotlin.ExperimentalStdlibApi")
		}
		dependsOn(tupleGeneratorTask)
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
