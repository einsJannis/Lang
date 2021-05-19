package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.lang.compiler.ir.ArgumentScope
import dev.einsjannis.lang.compiler.ir.FunctionCall
import dev.einsjannis.lang.compiler.ir.FunctionDefinition
import dev.einsjannis.lang.compiler.ir.ReturnType

class FunctionCallImpl internal constructor(identifierToken: Token, override val arguments: ArgumentScope) :
    FunctionCall {

    val name: String = identifierValueOf(identifierToken)

    override lateinit var functionDefinition: FunctionDefinition

    override val returnType: ReturnType get() = functionDefinition.returnType

}
