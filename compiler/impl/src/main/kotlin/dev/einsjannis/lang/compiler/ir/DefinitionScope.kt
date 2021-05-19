package dev.einsjannis.lang.compiler.ir

interface DefinitionScope : ScopeNode {

    override val children: List<Definition>

}
