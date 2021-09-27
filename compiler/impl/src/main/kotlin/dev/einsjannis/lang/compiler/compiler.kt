package dev.einsjannis.lang.compiler

import java.nio.file.Path
import kotlin.String
import kotlin.io.path.name
import kotlin.io.path.nameWithoutExtension
import kotlin.system.exitProcess

fun main(args: Array<String>) {
	if (args.isEmpty()) {
		System.err.println("Not enough source files.")
		exitProcess(1)
	}
	args.forEach {
		val langPath = Path.of(it)
		val llPath = langPath.parent.resolve(langPath.nameWithoutExtension + ".ll")
		llPath.toFile().writeText(compile(langPath))
		println("Compiled ${langPath.name} to $llPath")
	}
}

fun compile(path: Path): String {
	val tokens = lex(path)
	val functions = parse(tokens)
	functions.analyse()
	return generate(functions)
}
