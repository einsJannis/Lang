package dev.einsjannis.lang.compiler.ir.builtin

import dev.einsjannis.lang.compiler.ir.StructDefinition
import dev.einsjannis.lang.compiler.ir.TypeDefinition
import dev.einsjannis.lang.compiler.ir.internal.*

object Types {

    object Byte : TypeDefinition by TypeDefinitionImpl("Byte")

    object Integer : TypeDefinition by TypeDefinitionImpl("Int")

    object Long : TypeDefinition by TypeDefinitionImpl("Long")

    object Unit : TypeDefinition by TypeDefinitionImpl("Unit")

    object TypeMeta : StructDefinition by StructDefinitionImpl(
        "TypeMeta",
        StructVariableDefinitionScopeImpl(
            listOf(
                VariableDefinitionImpl("size", ReturnTypeImpl(Long, false), null)
            )
        )
    )

    class Blob private constructor(size: Int) : TypeDefinition by TypeDefinitionImpl("Blob$size") {

        companion object {

            internal val map: MutableMap<Int, Blob> = mutableMapOf()

            fun from(size: Int) = map.computeIfAbsent(size) { Blob(it) }

        }

    }

    val all = listOf(Byte, Integer, Long, Unit, TypeMeta) + Blob.map.values

}
