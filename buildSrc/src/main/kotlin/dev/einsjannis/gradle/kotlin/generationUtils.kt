package dev.einsjannis.gradle.kotlin

fun template(
    until: Int,
    separator: String = "",
    prefix: String = "",
    block: (Int) -> String
): String = (0 until until).joinToString(separator, prefix, transform = block)
