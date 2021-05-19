package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.lang.compiler.ir.StructDefinition
import dev.einsjannis.lang.compiler.ir.StructVariableDefinitionScope

internal class StructDefinitionImpl(
    identifierToken: Token,
    override val variableDefinitions: StructVariableDefinitionScope
) : StructDefinition {

    override val name: String = identifierValueOf(identifierToken)

}
