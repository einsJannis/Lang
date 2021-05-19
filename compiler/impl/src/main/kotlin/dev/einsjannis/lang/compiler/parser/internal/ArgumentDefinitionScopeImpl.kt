package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.lang.compiler.ir.ArgumentDefinition
import dev.einsjannis.lang.compiler.ir.ArgumentDefinitionScope

class ArgumentDefinitionScopeImpl(override val children: List<ArgumentDefinition>) : ArgumentDefinitionScope
