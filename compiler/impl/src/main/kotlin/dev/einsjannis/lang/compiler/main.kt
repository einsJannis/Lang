package dev.einsjannis.lang.compiler

import java.nio.file.Path

fun main() {
}

fun compile(`in`: Path, out: Path) {
	val tokens = lex(`in`)
	val tree = parse(tokens).also { analyse(it) }
	val outContent = generateLlvmIR(tree)
	out.toFile().writeText(outContent)
}
