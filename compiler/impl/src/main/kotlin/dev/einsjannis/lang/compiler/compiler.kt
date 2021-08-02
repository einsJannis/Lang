package dev.einsjannis.lang.compiler

import java.nio.file.Path
import kotlin.String

fun main(args: Array<String> ) {
	val inPath = Path.of(args[0])
	val outPath = Path.of(args[1])
	val out = compile(inPath)
	outPath.toFile().writeBytes(out)
}

fun compile(path: Path): ByteArray {
	val tokens = lex(path)
	val functions = parse(tokens)
	analyse(functions)
	val llvmIR = generateLlvmIR(functions)
	return llvmIR.toByteArray()
}
