package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.lang.compiler.ir.Expression
import dev.einsjannis.lang.compiler.ir.ReturnStatement

class ReturnStatementImpl(override val expression: Expression) : ReturnStatement
