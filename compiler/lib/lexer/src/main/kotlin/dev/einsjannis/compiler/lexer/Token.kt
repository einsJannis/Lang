package dev.einsjannis.compiler.lexer

import java.nio.file.Path

data class Token(
	val type: TokenType,
	val index: Int,
	val content: String,
	val path: Path
)
