package dev.einsjannis.lang.compiler.parser.internal

import dev.einsjannis.lang.compiler.ir.Code
import dev.einsjannis.lang.compiler.ir.CodeScope

class CodeScopeImpl(override val children: List<Code>) : CodeScope
