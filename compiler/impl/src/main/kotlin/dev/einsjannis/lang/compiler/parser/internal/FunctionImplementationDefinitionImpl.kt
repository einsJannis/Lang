package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.lang.compiler.ir.ArgumentDefinitionScope
import dev.einsjannis.lang.compiler.ir.CodeScope
import dev.einsjannis.lang.compiler.ir.FunctionImplementationDefinition
import dev.einsjannis.lang.compiler.ir.ReturnType

class FunctionImplementationDefinitionImpl(
    identifierToken: Token,
    override val arguments: ArgumentDefinitionScope,
    override val returnType: ReturnType,
    override val code: CodeScope
) : FunctionImplementationDefinition {

    override val name: String = identifierValueOf(identifierToken)

}
