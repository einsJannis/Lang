package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.lang.compiler.ir.String

class StringImpl(token: Token) : String {

    override val value: kotlin.String = token.content.let { it.substring(1, it.lastIndex) }

}
