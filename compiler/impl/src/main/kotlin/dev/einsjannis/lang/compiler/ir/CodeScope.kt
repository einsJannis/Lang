package dev.einsjannis.lang.compiler.ir

interface CodeScope : ScopeNode {

    override val children: List<Code>

}
