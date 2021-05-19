package dev.einsjannis.lang.compiler.semanticAnalysis.exception

class UnknownReferenceException(val identifier: String) : Exception() {

    override val message: String get() = "Referenced identifer $identifier with missing definition"

}
