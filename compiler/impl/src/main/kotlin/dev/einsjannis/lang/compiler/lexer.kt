package dev.einsjannis.lang.compiler

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.compiler.lexer.ignored
import dev.einsjannis.compiler.lexer.token
import java.nio.file.Path

object Tokens {
	val Identifier by token(Regex("[A-z_][A-z_0-9]*"))
	val KeywordFunction by token("fun")
	val KeywordVariable by token("var")
	val KeywordReturn by token("return")
	val SymbolBracketsParenthesesL by token("{")
	val SymbolBracketsParenthesesR by token("}")
	val SymbolBracketsBracesL by token("(")
	val SymbolBracketsBracesR by token(")")
	val SymbolComma by token(",")
	val SymbolColon by token(":")
	val SymbolSemiColon by token(";")
	val SymbolEqualSign by token("=")
	val PrimitiveNumber by token(Regex("0d[0-9]+|0b[01]+|0x[0-9A-f]+"))
	val PrimitiveBoolean by token(Regex("true|false"))
	val PrimitiveString by token(Regex("\".*\""))
	val PrimitiveChar by token(Regex("'.'"))
	val WhiteSpace by ignored(Regex("\\s"))
}

fun lex(path: Path): List<Token> = dev.einsjannis.compiler.lexer.Lexer(listOf(
	Tokens.KeywordFunction,
	Tokens.KeywordVariable,
	Tokens.KeywordReturn,
	Tokens.SymbolBracketsParenthesesL,
	Tokens.SymbolBracketsParenthesesR,
	Tokens.SymbolBracketsBracesL,
	Tokens.SymbolBracketsBracesR,
	Tokens.SymbolComma,
	Tokens.SymbolColon,
	Tokens.SymbolSemiColon,
	Tokens.SymbolEqualSign,
	Tokens.PrimitiveNumber,
	Tokens.PrimitiveBoolean,
	Tokens.PrimitiveString,
	Tokens.PrimitiveChar,
	Tokens.Identifier,
	Tokens.WhiteSpace
)).lex(path)
