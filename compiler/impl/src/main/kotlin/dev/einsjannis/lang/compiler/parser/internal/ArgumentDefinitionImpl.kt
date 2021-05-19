package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.lang.compiler.ir.ArgumentDefinition
import dev.einsjannis.lang.compiler.ir.Expression
import dev.einsjannis.lang.compiler.ir.ReturnType

class ArgumentDefinitionImpl(identifierToken: Token, override val returnType: ReturnType) : ArgumentDefinition {

    override val name: String = identifierValueOf(identifierToken)

    override val initialization: Expression? get() = null

}
