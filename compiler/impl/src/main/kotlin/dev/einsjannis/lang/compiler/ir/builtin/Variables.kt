package dev.einsjannis.lang.compiler.ir.builtin

import dev.einsjannis.lang.compiler.ir.VariableDefinition
import dev.einsjannis.lang.compiler.ir.internal.ReturnTypeImpl
import dev.einsjannis.lang.compiler.ir.internal.VariableDefinitionImpl

object Variables {

    val unitInstance: VariableDefinition = VariableDefinitionImpl(
        "unit",
        ReturnTypeImpl(
            Types.Unit,
            isPointer = false
        ),
        null
    )

    val all: List<VariableDefinition> = Types.all.map { it.typeVariableDefinition } + listOf(unitInstance)

}
