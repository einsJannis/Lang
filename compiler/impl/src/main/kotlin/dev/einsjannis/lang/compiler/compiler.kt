package dev.einsjannis.lang.compiler

import java.nio.file.Path
import kotlin.String

fun main() {
	println(compile(Path.of("example","helloworld.lang")))
}

fun compile(path: Path): String {
	val tokens = lex(path)
	val functions = parse(tokens)
	functions.analyse()
	return generate(functions)
}
