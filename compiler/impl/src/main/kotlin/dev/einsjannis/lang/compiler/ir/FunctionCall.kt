package dev.einsjannis.lang.compiler.ir

interface FunctionCall : Expression {

    val functionDefinition: FunctionDefinition

    val arguments: ArgumentScope

}
