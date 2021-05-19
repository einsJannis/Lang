package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.lang.compiler.ir.AssignmentStatement
import dev.einsjannis.lang.compiler.ir.Expression
import dev.einsjannis.lang.compiler.ir.VariableCall

class AssignmentStatementImpl(
    override val variableCall: VariableCall,
    override val expression: Expression
) : AssignmentStatement
