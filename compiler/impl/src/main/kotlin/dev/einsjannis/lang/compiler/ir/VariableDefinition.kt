package dev.einsjannis.lang.compiler.ir

interface VariableDefinition : Definition {

    val initialization: Expression?

    val returnType: ReturnType

}
