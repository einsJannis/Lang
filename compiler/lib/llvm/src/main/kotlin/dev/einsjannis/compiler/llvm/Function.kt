package dev.einsjannis.compiler.llvm

sealed class Function constructor(
	override val name: String,
	val returnType: Type,
	val arguments: MutableList<Argument> = mutableListOf(),
) : IRElement.Named.Global {

	data class Argument(
		override val name: String,
		override val type: Type
	) : IRElement.Named.Local {

		override fun generateIR(): String = "$type ${generateNameIR()}"

	}

	class FunctionImplementation internal constructor(
		name: String,
		returnType: Type,
		arguments: MutableList<Argument> = mutableListOf(),
		val code: MutableList<Code> = mutableListOf()
	) : Function(name, returnType, arguments) {

		fun addFunctionCall(function: Function, arguments: List<Variable>, returnName: String): Variable =
			Code.FunctionCall(function, arguments, returnName).also { code.add(it) }

		fun addReturnStatement(returnVar: Variable) {
			Code.Return(returnVar).also { code.add(it) }
		}

		fun addVarAlias(variable: Variable, varName: String): Variable =
			Code.VarAlias(variable, varName).also { code.add(it) }

		override fun generateIR(): String = """
		define ${returnType.generateNameIR()} ${generateNameIR()}(${arguments.joinToString { it.generateIR() }}) {
		${code.joinToString { "    " + it.generateIR() }}
		}
		""".trimIndent()

		fun addPrimitive(primitiveValue: PrimitiveValue, varName: String): Variable =
			Code.Primitive(primitiveValue, varName)

		fun addAllocationCall(type: Type, varName: String): Variable =
			Code.AllocCall(type, varName)

	}

	class FunctionDeclaration internal constructor(
		name: String,
		returnType: Type,
		arguments: MutableList<Argument> = mutableListOf(),
	) : Function(name, returnType, arguments) {

		override fun generateIR(): String = """
		declare ${returnType.generateNameIR()} ${generateNameIR()}(${arguments.joinToString { it.generateIR() }})
		""".trimIndent()

	}

	override val type: Type get() = Type.BuiltIn.FunctionType(this)

	fun addArgument(name: String, type: Type): Variable {
		val argument = Argument(name, type)
		arguments.add(argument)
		return argument
	}

}
