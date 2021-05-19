package dev.einsjannis.lang.compiler.ir

interface ArgumentScope : ScopeNode {

    override val children: List<Expression>

}
