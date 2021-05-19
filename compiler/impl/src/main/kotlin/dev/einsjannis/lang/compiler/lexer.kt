package dev.einsjannis.lang.compiler

import dev.einsjannis.compiler.lexer.Lexer
import dev.einsjannis.compiler.lexer.ignored
import dev.einsjannis.compiler.lexer.token
import java.nio.file.Path

object Token {
    object Keyword {
        val Struct by token(Regex.fromLiteral("struct"))
        val Function by token(Regex.fromLiteral("fun"))
        val Variable by token(Regex.fromLiteral("var"))
        val If by token(Regex.fromLiteral("if"))
        val Else by token(Regex.fromLiteral("else"))
        val While by token(Regex.fromLiteral("while"))
        val Return by token(Regex.fromLiteral("return"))
    }
    object Symbol {
        object Brackets {
            val ParenthesesL by token(Regex.fromLiteral("{"))
            val ParenthesesR by token(Regex.fromLiteral("}"))
            val BracesL by token(Regex.fromLiteral("("))
            val BracesR by token(Regex.fromLiteral(")"))
        }
        /*object Operator {
            val Plus by token(Regex.fromLiteral("+"))
            val Minus by token(Regex.fromLiteral("-"))
            val And by token(Regex.fromLiteral("&"))
            val Or by token(Regex.fromLiteral("|"))
            val Not by token(Regex.fromLiteral("!"))
            val Equals by token(Regex.fromLiteral("=="))
            val LessThenOrEqual by token(Regex.fromLiteral("<="))
            val LessThen by token(Regex.fromLiteral("<"))
            val GreaterThenOrEqual by token(Regex.fromLiteral(">="))
            val GreaterThen by token(Regex.fromLiteral(">"))
        }*/
        val Assign by token(Regex.fromLiteral("="))
        val Comma by token(Regex.fromLiteral(","))
        val SemiColon by token(Regex.fromLiteral(";"))
        val ReturnType by token(Regex.fromLiteral(":"))
        val Dot by token(Regex.fromLiteral("."))
        val Star by token(Regex.fromLiteral("*"))
    }
    object Primitive {
        val Number by token(Regex("([0-9]+|0x[0-9a-fA-F]+|0b[01]+)(b|i|L|B[0-9]+)?"))
        val String by token(Regex("\".*\""))
        val Char by token(Regex("\'.\'"))
        val Boolean by token(Regex("true|false"))
    }
    val Identifier by token(Regex("[A-Za-z_][A-Za-z_0-9]*|`.*`"))
    val WhiteSpace by ignored(Regex("\\s+"))
}

private val lexer = Lexer(
    listOf(
        Token.WhiteSpace,
        Token.Keyword.Struct,
        Token.Keyword.Function,
        Token.Keyword.Variable,
        Token.Keyword.If,
        Token.Keyword.Else,
        Token.Keyword.While,
        Token.Keyword.Return,
        Token.Identifier,
        Token.Symbol.Comma,
        Token.Symbol.SemiColon,
        Token.Symbol.Assign,
        Token.Symbol.ReturnType,
        Token.Symbol.Dot,
        Token.Symbol.Brackets.ParenthesesL,
        Token.Symbol.Brackets.ParenthesesR,
        Token.Symbol.Brackets.BracesL,
        Token.Symbol.Brackets.BracesR,
        Token.Primitive.Boolean,
        Token.Primitive.Char,
        Token.Primitive.String,
        Token.Primitive.Number
    )
)

fun lex(path: Path) = lexer.lex(path)
