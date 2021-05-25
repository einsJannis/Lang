package dev.einsjannis.compiler.llvm

class Function internal constructor(
	override val name: String,
	val returnType: Type,
	private val arguments: MutableList<Argument> = mutableListOf(),
	private val code: MutableList<Code> = mutableListOf()
) : GlobalIRElement {

	data class Argument(
		override val name: String,
		val type: Type
	) : LocalIRElement {

		override fun generateIR(): String = "$type ${generateNameIR()}"

	}

	fun addArgument(name: String, type: Type): NamedIRElement {
		val argument = Argument(name, type)
		arguments.add(argument)
		return argument
	}

	fun addFunctionCall(function: Function, arguments: List<NamedIRElement>, returnName: String): NamedIRElement {
		TODO()
	}

	fun addStructVariableCall(variable: NamedIRElement, fieldName: String, returnName: String): NamedIRElement {
		TODO()
	}

	fun addVariable(old: NamedIRElement, newName: String): NamedIRElement {
		TODO()
	}

	fun addReturnStatement(returnVar: NamedIRElement) {
		code.add(Code.Return(returnVar))
	}

	override fun generateIR(): String =
"""
define ${returnType.generateNameIR()} ${generateNameIR()}(${arguments.joinToString { it.generateIR() }}) {
${code.joinToString { "    " + it.generateIR() }}
}
"""

}
