package dev.einsjannis.lang.compiler.ir.internal

import dev.einsjannis.lang.compiler.ir.StructDefinition
import dev.einsjannis.lang.compiler.ir.StructVariableDefinitionScope

data class StructDefinitionImpl(
    override val name: String,
    override val variableDefinitions: StructVariableDefinitionScope
) : StructDefinition
