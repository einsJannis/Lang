package dev.einsjannis.lang.compiler.ir

interface VariableCall : Expression {

    val variableDefinition: VariableDefinition

    val parent: Expression?

}
