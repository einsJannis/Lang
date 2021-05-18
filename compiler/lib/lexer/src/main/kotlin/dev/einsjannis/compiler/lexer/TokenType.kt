package dev.einsjannis.compiler.lexer

class TokenType(
	val regex: Regex,
	val name: String,
	val ignore: Boolean = false
)
