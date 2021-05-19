package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.lang.compiler.ir.CodeScope
import dev.einsjannis.lang.compiler.ir.ConditionStatement
import dev.einsjannis.lang.compiler.ir.Expression

class ConditionStatementImpl(override val condition: Expression, override val code: CodeScope) : ConditionStatement
