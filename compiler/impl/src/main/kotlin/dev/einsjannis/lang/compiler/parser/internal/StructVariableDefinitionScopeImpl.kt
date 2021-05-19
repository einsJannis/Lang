package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.lang.compiler.ir.StructVariableDefinitionScope
import dev.einsjannis.lang.compiler.ir.VariableDefinition

class StructVariableDefinitionScopeImpl(override val children: List<VariableDefinition>) : StructVariableDefinitionScope
