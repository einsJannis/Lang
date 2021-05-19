package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.lang.compiler.ir.Number
import dev.einsjannis.lang.compiler.ir.ReturnType
import dev.einsjannis.lang.compiler.ir.builtin.Types
import dev.einsjannis.lang.compiler.ir.internal.ReturnTypeImpl

class NumberImpl(token: Token) : Number {

    private val typeDef = Regex("b|i|L|B[0-9]+").find(token.content)?.value

    override val bytes: ByteArray =
        bytesFromString(typeDef?.let { token.content.substring(0, token.content.length - it.length) } ?: token.content)

    override val returnType: ReturnType = ReturnTypeImpl(
        (when (typeDef) {
            "b"  -> Types.Byte
            "i"  -> Types.Integer
            "L"  -> Types.Long
            null -> Types.Integer
            else -> Types.Blob.from(typeDef.substring(1).toInt(10))
        }), false
    )

}
