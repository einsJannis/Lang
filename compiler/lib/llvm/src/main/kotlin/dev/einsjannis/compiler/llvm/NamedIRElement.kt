package dev.einsjannis.compiler.llvm

interface NamedIRElement : IRElement {

	val name: String

	fun generateNameIR(): String

}
