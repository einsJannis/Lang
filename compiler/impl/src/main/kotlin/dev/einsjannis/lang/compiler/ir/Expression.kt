package dev.einsjannis.lang.compiler.ir

sealed interface Expression : Code {

    val returnType: ReturnType

}
