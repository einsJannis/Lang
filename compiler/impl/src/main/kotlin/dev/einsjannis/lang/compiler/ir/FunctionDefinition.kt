package dev.einsjannis.lang.compiler.ir

interface FunctionDefinition : Definition {

    val arguments: ArgumentDefinitionScope

    val returnType: ReturnType

}
