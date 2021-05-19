package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.lang.compiler.ir.Definition
import dev.einsjannis.lang.compiler.ir.DefinitionScope

class DefinitionScopeImpl(override val children: List<Definition>) : DefinitionScope
