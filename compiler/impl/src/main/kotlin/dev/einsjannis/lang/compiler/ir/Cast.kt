package dev.einsjannis.lang.compiler.ir

interface Cast : Expression {

    val newType: ReturnType

    val expression: Expression

    override val returnType: ReturnType
        get() = newType

}
