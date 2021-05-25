package dev.einsjannis.compiler.llvm

interface GlobalIRElement : NamedIRElement {

	override fun generateNameIR(): String = "@$name"

}
