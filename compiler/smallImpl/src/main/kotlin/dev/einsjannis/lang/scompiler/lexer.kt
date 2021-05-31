package dev.einsjannis.lang.scompiler

import dev.einsjannis.compiler.lexer.Lexer
import dev.einsjannis.compiler.lexer.ignored
import dev.einsjannis.compiler.lexer.token
import java.nio.file.Path

object Token {

	val WhiteSpace by ignored(Regex("\\w+"))

	val Identifier by token(Regex("[A-Za-z_][A-Za-z0-9_]*"))

	object Keyword {

		val Function by token("fun")

		val Variable by token("var")

		val Constant by token("const")

		val If by token("if")

		val Else by token("else")

	}

	object Symbol {

		val BracesL by token("{")

		val BracesR by token("}")

		val ParenthesesL by token("(")

		val ParenthesesR by token(")")

		val LessThen by token("<")

		val GreaterThen by token(">")

		val Comma by token(",")

		val Dot by token(".")

		val Colon by token(":")

		val SemiColon by token(";")

		val EqualSign by token("=")

	}

	object Primitive {

		object Number {

			val Integer by token(Regex("[0-9]+|0b[01]+|0x[0-9a-fA-F]+"))

			val Float by token(Regex("[0-9]*\\.[0-9]+"))

		}

		val Boolean by token(Regex("true|false"))

		val Char by token(Regex("'.'"))

		val String by token(Regex("\".*\""))

	}

}

val lexer = Lexer(
	listOf(
		Token.WhiteSpace,
		Token.Identifier,
		Token.Keyword.Function,
		Token.Symbol.BracesL,
		Token.Symbol.BracesR,
		Token.Symbol.ParenthesesL,
		Token.Symbol.ParenthesesR,
		Token.Symbol.LessThen,
		Token.Symbol.GreaterThen,
		Token.Symbol.Comma,
		Token.Symbol.Dot,
		Token.Symbol.Colon,
		Token.Symbol.SemiColon,
		Token.Symbol.EqualSign,
		Token.Primitive.Number.Integer,
		Token.Primitive.Number.Float,
		Token.Primitive.Char,
		Token.Primitive.String,
	)
)

fun lex(path: Path) = lexer.lex(path)
