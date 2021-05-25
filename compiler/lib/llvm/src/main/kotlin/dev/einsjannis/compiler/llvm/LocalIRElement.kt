package dev.einsjannis.compiler.llvm

interface LocalIRElement : NamedIRElement {

	override fun generateNameIR(): String = "%$name"

}
