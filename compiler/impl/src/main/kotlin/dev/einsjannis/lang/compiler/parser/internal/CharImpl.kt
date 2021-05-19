package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.lang.compiler.ir.Char

class CharImpl(token: Token) : Char {

    override val value: kotlin.Char = token.content[1]

}
