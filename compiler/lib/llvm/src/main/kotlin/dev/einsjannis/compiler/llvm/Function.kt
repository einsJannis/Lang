package dev.einsjannis.compiler.llvm

class Function internal constructor(
	override val name: String,
	val returnType: Type,
	val arguments: MutableList<Argument> = mutableListOf(),
	val code: MutableList<Code> = mutableListOf()
) : IRElement.Named.Global {

	data class Argument(
		override val name: String,
		override val type: Type
	) : IRElement.Named.Local {

		override fun generateIR(): String = "$type ${generateNameIR()}"

	}

	override val type: Type get() = Type.BuiltIn.FunctionType(this)

	fun addArgument(name: String, type: Type): Variable {
		val argument = Argument(name, type)
		arguments.add(argument)
		return argument
	}

	fun addFunctionCall(function: Function, arguments: List<Variable>, returnName: String): Variable =
		Code.FunctionCall(function, arguments, returnName).also { code.add(it) }

	fun addStructVariableCall(struct: Type.StructType, variable: Variable, fieldName: String, returnName: String): Variable =
		Code.StructVariableCall(struct, fieldName, variable, returnName).also { code.add(it) }

	fun addReturnStatement(returnVar: Variable) {
		Code.Return(returnVar).also { code.add(it) }
	}

	fun addVarAlias(variable: Variable, varName: String): Variable =
		Code.VarAlias(variable, varName).also { code.add(it) }


	override fun generateIR(): String =
"""
define ${returnType.generateNameIR()} ${generateNameIR()}(${arguments.joinToString { it.generateIR() }}) {
${code.joinToString { "    " + it.generateIR() }}
}
"""

}
