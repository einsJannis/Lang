package dev.einsjannis.compiler.llvm

interface Code : IRElement {

	class Return(
		val value: NamedIRElement
	) : Code {

		override fun generateIR(): String = "ret $value"

	}

}
