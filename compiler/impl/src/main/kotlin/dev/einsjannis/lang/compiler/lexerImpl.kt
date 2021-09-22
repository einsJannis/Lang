package dev.einsjannis.lang.compiler

import dev.einsjannis.Tuple2
import dev.einsjannis.compiler.lexer.Lexer
import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.compiler.lexer.ignored
import dev.einsjannis.compiler.lexer.token
import dev.einsjannis.compiler.parser.pattern
import dev.einsjannis.tupleOf
import dev.einsjannis.compiler.parser.Pattern
import java.nio.file.Path
import kotlin.String

object Tokens {
	object Keyword {
		val Fun by token("fun")
		val Var by token("var")
		val If by token("if")
		val Else by token("else")
		val Return by token("return")
	}
	object Symbol {
		val ParenthesesL by token("(")
		val ParenthesesR by token(")")
		val ParenthesesPattern: Tuple2<Pattern<*>, Pattern<Token>, Pattern<Token>> =
			tupleOf(ParenthesesL.pattern, ParenthesesR.pattern)
		val BracesL by token("{")
		val BracesR by token("}")
		val BracesPattern: Tuple2<Pattern<*>, Pattern<Token>, Pattern<Token>> =
			tupleOf(BracesL.pattern, BracesR.pattern)
		val Colon by token(":")
		val SemiColon by token(";")
		val Comma by token(",")
		val EqualSign by token("=")
	}
	object Primitive {
		val Number by token(Regex("[0-9]+b?"))
		val Char by token(Regex("'.'"))
		//val String by token(Regex("\".*\""))
		val Boolean by token(Regex("true|false"))
	}
	val Identifier by token(Regex("[A-z_][A-z_0-9]*"))
	val WhiteSpace by ignored(Regex("\\s"))
}

fun lex(path: Path): List<Token> = Lexer(listOf(
	Tokens.Keyword.Fun,
	Tokens.Keyword.Var,
	Tokens.Keyword.If,
	Tokens.Keyword.Else,
	Tokens.Keyword.Return,
	Tokens.Symbol.ParenthesesL,
	Tokens.Symbol.ParenthesesR,
	Tokens.Symbol.BracesL,
	Tokens.Symbol.BracesR,
	Tokens.Symbol.Colon,
	Tokens.Symbol.SemiColon,
	Tokens.Symbol.Comma,
	Tokens.Symbol.EqualSign,
	Tokens.Primitive.Number,
	Tokens.Primitive.Char,
	//Tokens.Primitive.String,
	Tokens.Primitive.Boolean,
	Tokens.Identifier,
	Tokens.WhiteSpace
)).lex(path)
