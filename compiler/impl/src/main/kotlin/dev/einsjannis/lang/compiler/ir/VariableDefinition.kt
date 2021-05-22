package dev.einsjannis.lang.compiler.ir

interface VariableDefinition : Code, Definition {

    val initialization: Expression?

    val returnType: ReturnType

}
