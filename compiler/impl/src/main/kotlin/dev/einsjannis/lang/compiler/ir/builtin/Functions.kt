package dev.einsjannis.lang.compiler.ir.builtin

import dev.einsjannis.lang.compiler.ir.FunctionDefinition
import dev.einsjannis.lang.compiler.ir.internal.ArgumentDefinitionImpl
import dev.einsjannis.lang.compiler.ir.internal.ArgumentDefinitionScopeImpl
import dev.einsjannis.lang.compiler.ir.internal.FunctionDefinitionImpl
import dev.einsjannis.lang.compiler.ir.internal.ReturnTypeImpl

object Functions {

    object IAdd : FunctionDefinition by FunctionDefinitionImpl(
        "iAdd",
        ArgumentDefinitionScopeImpl(
            listOf(
                ArgumentDefinitionImpl("a", ReturnTypeImpl(Types.Integer, false)),
                ArgumentDefinitionImpl("b", ReturnTypeImpl(Types.Integer, false))
            )
        ),
        ReturnTypeImpl(Types.Integer, false)
    )

    object IToString : FunctionDefinition by FunctionDefinitionImpl(
        "iToString",
        ArgumentDefinitionScopeImpl(listOf(ArgumentDefinitionImpl("int", ReturnTypeImpl(Types.Integer, false)))),
        ReturnTypeImpl(Types.Byte, true)
    )

    object Print : FunctionDefinition by FunctionDefinitionImpl(
        "print",
        ArgumentDefinitionScopeImpl(listOf(ArgumentDefinitionImpl("string", ReturnTypeImpl(Types.Byte, true)))),
        ReturnTypeImpl(Types.Unit, false)
    )

    val all = listOf<FunctionDefinition>(IAdd, IToString, Print)

}
