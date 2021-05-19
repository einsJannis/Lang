package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.lang.compiler.ir.Cast
import dev.einsjannis.lang.compiler.ir.Expression
import dev.einsjannis.lang.compiler.ir.ReturnType

class CastImpl(override val newType: ReturnType, override val expression: Expression) : Cast
