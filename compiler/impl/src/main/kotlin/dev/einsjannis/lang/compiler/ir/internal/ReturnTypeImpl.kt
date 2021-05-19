package dev.einsjannis.lang.compiler.ir.internal

import dev.einsjannis.lang.compiler.ir.ReturnType
import dev.einsjannis.lang.compiler.ir.TypeDefinition

data class ReturnTypeImpl(
    override val typeDefinition: TypeDefinition,
    override val isPointer: kotlin.Boolean
) : ReturnType
