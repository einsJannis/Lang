package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.lang.compiler.ir.Boolean

class BooleanImpl(token: Token) : Boolean {

    override val value: kotlin.Boolean = token.content.toBoolean()

}
