import java.io.FileFilter

plugins {
	kotlin("jvm") version "1.5.0" apply false
	application
}

application {
	mainClass.set("dev.einsjannis.lang.compiler.CompilerKt")
}

dependencies {
	implementation(project(":compiler"))
}
tasks.named("clean") {
	doLast {
		file("example").listFiles(FileFilter {
			it.extension == "ll"
		})?.forEach { it.delete() }
	}
}
repositories {
	mavenCentral()
}
