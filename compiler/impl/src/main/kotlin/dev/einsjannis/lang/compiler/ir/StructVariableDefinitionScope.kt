package dev.einsjannis.lang.compiler.ir

interface StructVariableDefinitionScope : ScopeNode {

    override val children: List<VariableDefinition>

}
