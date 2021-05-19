package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.lang.compiler.ir.Expression
import dev.einsjannis.lang.compiler.ir.ReturnType
import dev.einsjannis.lang.compiler.ir.VariableCall
import dev.einsjannis.lang.compiler.ir.VariableDefinition

class VariableCallImpl internal constructor(identifierToken: Token, override var parent: Expression?) : VariableCall {

    val name = identifierValueOf(identifierToken)

    override lateinit var variableDefinition: VariableDefinition

    override val returnType: ReturnType get() = variableDefinition.returnType

}
