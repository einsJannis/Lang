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

		override fun generateIR(): String = "${type.generateNameIR()} ${generateNameIR()}"

	}

	class FunctionImplementation internal constructor(
		name: String,
		returnType: Type,
		arguments: MutableList<Argument> = mutableListOf(),
		val code: MutableList<Code> = mutableListOf(),
	) : Function(name, returnType, arguments) {

		override fun generateIR(): String {
			if (code.last() !is Code.Return) throw RuntimeException()
			return """
			|define ${returnType.generateNameIR()} ${generateNameIR()}(${arguments.joinToString { it.generateIR() }}) {
			|${code.joinToString(separator = "\n") { if (it is Code.Label) it.generateIR() else "    " + it.generateIR() }}
			|}
			""".trimMargin("|")
		}

		fun addNotSavedFunctionCall(function: Function, arguments: List<Variable>): Unit {
			Code.NotSavedFunctionCall(function, arguments).also { code.add(it) }
		}

		fun addFunctionCall(function: Function, arguments: List<Variable>, returnName: String): Variable =
			Code.FunctionCall(function, arguments, returnName).also { code.add(it) }

		fun addReturnCall(returnVar: Variable) {
			Code.Return(returnVar).also { code.add(it) }
		}

		fun addVarAlias(variable: Variable, varName: String): Variable =
			Code.VarAlias(variable, varName).also { code.add(it) }

		fun addPrimitive(primitiveValue: PrimitiveValue): Variable =
			Code.Primitive(primitiveValue)

		fun addAllocationCall(type: Type.BuiltIn.PointerType<Type>, varName: String): Variable =
			Code.AllocCall(type, varName).also { code.add(it) }

		fun addStoreCall(from: Variable, to: Variable) =
			Code.StoreCall(from, to).also { code.add(it) }.let { to }

		fun addLabel(name: String) =
			Code.Label(name).also { code.add(it) }

		fun addIcmpCall(operator: Code.IcmpCall.Operator, op1: Variable, op2: Variable, name: String): Variable =
			Code.IcmpCall(operator, op1, op2, name).also { code.add(it) }

		fun addBrCall(label: String) =
			Code.UBrCall(label).also { code.add(it) }

		fun addBrCall(conditionRes: Variable, ifLabelName: String, elseLabelName: String) =
			Code.BrCall(conditionRes, ifLabelName, elseLabelName).also { code.add(it) }

		fun addLoadCall(ptr: Variable, name: String, type: Type = (ptr.type as? Type.BuiltIn.PointerType<*>)?.child ?: throw RuntimeException()): Variable =
			Code.LoadCall(name, ptr, type).also { code.add(it) }

		fun addAddCall(a: Variable, b: Variable, varName: String): Variable =
			Code.AddCall(a, b, varName).also { code.add(it) }

		fun addSubCall(a: Variable, b: Variable, varName: String): Variable =
			Code.SubCall(a, b, varName).also { code.add(it) }

		fun addMulCall(a: Variable, b: Variable, varName: String): Variable =
			Code.MulCall(a, b, varName).also { code.add(it) }

		fun addSDivCall(a: Variable, b: Variable, varName: String): Variable =
			Code.SDivCall(a, b, varName).also { code.add(it) }

		fun addSRemCall(a: Variable, b: Variable, varName: String): Variable =
			Code.SRemCall(a, b, varName).also { code.add(it) }

		fun addShlCall(a: Variable, b: Variable, varName: String): Variable =
			Code.ShlCall(a, b, varName).also { code.add(it) }

		fun addLShrCall(a: Variable, b: Variable, varName: String): Variable =
			Code.LShrCall(a, b, varName).also { code.add(it) }

		fun addAndCall(a: Variable, b: Variable, varName: String): Variable =
			Code.AndCall(a, b, varName).also { code.add(it) }

		fun addOrCall(a: Variable, b: Variable, varName: String): Variable =
			Code.OrCall(a, b, varName).also { code.add(it) }

		fun addXOrCall(a: Variable, b: Variable, varName: String): Variable =
			Code.XOrCall(a, b, varName).also { code.add(it) }

		fun addBitCast(a: Variable, to: Type, varName: String): Variable =
			Code.BitCast(a, to,  varName).also { code.add(it) }

		fun addZext(a: Variable, to: Type, varName: String): Variable =
			Code.Zext(a, to, varName).also { code.add(it) }

		fun addTrunc(a: Variable, to: Type, varName: String): Variable =
			Code.Trunc(a, to, varName).also { code.add(it) }

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
