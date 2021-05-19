package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.lang.compiler.ir.ReturnType
import dev.einsjannis.lang.compiler.ir.TypeDefinition

class ReturnTypeImpl internal constructor(identifierToken: Token, override val isPointer: Boolean) : ReturnType {

    val name = identifierValueOf(identifierToken)

    override lateinit var typeDefinition: TypeDefinition

}
