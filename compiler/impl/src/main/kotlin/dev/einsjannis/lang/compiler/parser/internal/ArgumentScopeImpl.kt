package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.lang.compiler.ir.ArgumentScope
import dev.einsjannis.lang.compiler.ir.Expression

class ArgumentScopeImpl(override val children: List<Expression>) : ArgumentScope
