package dev.einsjannis.lang.compiler.ir

import dev.einsjannis.lang.compiler.ir.builtin.Types
import dev.einsjannis.lang.compiler.ir.internal.ReturnTypeImpl

interface String : Primitive {

    val value: kotlin.String

    override val bytes: ByteArray
        get() = value.toByteArray()

    override val returnType: ReturnType
        get() = ReturnTypeImpl(Types.Byte, true)

}
