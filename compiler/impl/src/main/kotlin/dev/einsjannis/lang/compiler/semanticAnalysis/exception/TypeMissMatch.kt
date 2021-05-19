package dev.einsjannis.lang.compiler.semanticAnalysis.exception

import dev.einsjannis.lang.compiler.ir.ReturnType

abstract class TypeMissMatch : Exception() {

    abstract val expected: ReturnType

    abstract val found: ReturnType

}
