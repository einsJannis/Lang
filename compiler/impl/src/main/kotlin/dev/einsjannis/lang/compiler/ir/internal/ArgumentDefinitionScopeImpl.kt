package dev.einsjannis.lang.compiler.ir.internal

import dev.einsjannis.lang.compiler.ir.ArgumentDefinition
import dev.einsjannis.lang.compiler.ir.ArgumentDefinitionScope

data class ArgumentDefinitionScopeImpl(override val children: List<ArgumentDefinition>) : ArgumentDefinitionScope
