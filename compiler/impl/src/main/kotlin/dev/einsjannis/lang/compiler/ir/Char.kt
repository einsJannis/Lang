package dev.einsjannis.lang.compiler.ir

import dev.einsjannis.lang.compiler.ir.builtin.Types
import dev.einsjannis.lang.compiler.ir.internal.ReturnTypeImpl

interface Char : Primitive {

    val value: kotlin.Char

    override val bytes: ByteArray
        get() = byteArrayOf(value.toByte())

    override val returnType: ReturnType
        get() = ReturnTypeImpl(Types.Byte, false)

}
