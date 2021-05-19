package dev.einsjannis.lang.compiler.ir

interface AssignmentStatement : Code {

    val variableCall: VariableCall

    val expression: Expression

}
