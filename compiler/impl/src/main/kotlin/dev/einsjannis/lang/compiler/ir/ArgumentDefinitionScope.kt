package dev.einsjannis.lang.compiler.ir

interface ArgumentDefinitionScope : ScopeNode {

    override val children: List<ArgumentDefinition>

}
