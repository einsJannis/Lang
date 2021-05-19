package dev.einsjannis.lang.compiler.ir

interface ConditionStatement : Code {

    val condition: Expression

    val code: CodeScope

}
