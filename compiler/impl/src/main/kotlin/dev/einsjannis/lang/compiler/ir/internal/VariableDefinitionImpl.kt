package dev.einsjannis.lang.compiler.ir.internal

import dev.einsjannis.lang.compiler.ir.Expression
import dev.einsjannis.lang.compiler.ir.ReturnType
import dev.einsjannis.lang.compiler.ir.VariableDefinition

data class VariableDefinitionImpl(
    override val name: String,
    override val returnType: ReturnType,
    override val initialization: Expression?
) : VariableDefinition
