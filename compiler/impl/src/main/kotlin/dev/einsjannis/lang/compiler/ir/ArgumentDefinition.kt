package dev.einsjannis.lang.compiler.ir

interface ArgumentDefinition : VariableDefinition {

    override val initialization: Expression? get() = null

}
