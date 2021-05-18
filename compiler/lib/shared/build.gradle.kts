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

	withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions {
			jvmTarget = "1.8"
			optIn("kotlin.contracts.ExperimentalContracts")
			optIn("kotlin.ExperimentalStdlibApi")
		}
	}

	register<dev.einsjannis.gradle.kotlin.TupleGeneratorTask>("tupleGeneratorTask") {
		outputDirectory.set(file("src/main/generated"))
		amountOfTuples.set(16)
	}

}

fun org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions.optIn(annotation: String) {
	freeCompilerArgs = freeCompilerArgs + "-Xopt-in=$annotation"
}
