package dev.einsjannis.lang.compiler.ir.internal

import dev.einsjannis.lang.compiler.ir.ArgumentDefinition
import dev.einsjannis.lang.compiler.ir.ReturnType

internal data class ArgumentDefinitionImpl(
    override val name: String,
    override val returnType: ReturnType
) : ArgumentDefinition
