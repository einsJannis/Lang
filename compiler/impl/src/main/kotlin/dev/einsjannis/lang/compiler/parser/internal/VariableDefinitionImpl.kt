package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.lang.compiler.ir.Expression
import dev.einsjannis.lang.compiler.ir.ReturnType
import dev.einsjannis.lang.compiler.ir.VariableDefinition

class VariableDefinitionImpl(
    identifierToken: Token,
    override val returnType: ReturnType,
    override val initialization: Expression?
) : VariableDefinition {

    override val name: String = identifierValueOf(identifierToken)

}
