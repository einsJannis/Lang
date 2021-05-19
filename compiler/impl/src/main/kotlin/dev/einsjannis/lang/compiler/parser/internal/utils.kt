package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.compiler.lexer.Token

internal fun identifierValueOf(token: Token) = if (token.content.let { it.startsWith("`") || it.endsWith("`") }) {
    token.content.substring(1, token.content.lastIndex)
} else token.content

private fun removeLines(string: String) = string.filterNot { it == '_' }

private fun radixAndDigits(string: String) = when {
    string.startsWith("0x") -> Pair(16, removeLines(string.substring(2)))
    string.startsWith("0b") -> Pair(2, removeLines(string.substring(2)))
    else                    -> Pair(10, removeLines(string))
}

internal fun bytesFromString(string: String): ByteArray {
    val (radix, string) = radixAndDigits(string)
    val bigInt = string.toBigInteger(radix)
    return bigInt.toByteArray()
}
