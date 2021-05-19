package dev.einsjannis.lang.compiler.ir

interface StructDefinition : TypeDefinition {

    val variableDefinitions: StructVariableDefinitionScope

}
