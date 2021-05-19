package dev.einsjannis.lang.compiler.ir

import dev.einsjannis.lang.compiler.ir.builtin.Types
import dev.einsjannis.lang.compiler.ir.internal.ReturnTypeImpl
import dev.einsjannis.lang.compiler.ir.internal.VariableDefinitionImpl

interface TypeDefinition : Definition {

    val typeVariableDefinition: VariableDefinition
        get() = VariableDefinitionImpl(name, ReturnTypeImpl(Types.TypeMeta, false), null)

}
