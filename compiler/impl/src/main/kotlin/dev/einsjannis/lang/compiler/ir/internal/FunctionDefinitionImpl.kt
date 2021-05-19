package dev.einsjannis.lang.compiler.ir.internal

import dev.einsjannis.lang.compiler.ir.ArgumentDefinitionScope
import dev.einsjannis.lang.compiler.ir.FunctionDefinition
import dev.einsjannis.lang.compiler.ir.ReturnType

internal data class FunctionDefinitionImpl(
    override val name: String,
    override val arguments: ArgumentDefinitionScope,
    override val returnType: ReturnType
) : FunctionDefinition
