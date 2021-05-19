package dev.einsjannis.lang.compiler.ir

import dev.einsjannis.lang.compiler.ir.builtin.Types
import dev.einsjannis.lang.compiler.ir.internal.ReturnTypeImpl

interface Boolean : Primitive {

    val value: kotlin.Boolean

    override val bytes: ByteArray get() = if (value) byteArrayOf(0x01b) else byteArrayOf(0x00b)

    override val returnType: ReturnType
        get() = ReturnTypeImpl(Types.Byte, false)

}
