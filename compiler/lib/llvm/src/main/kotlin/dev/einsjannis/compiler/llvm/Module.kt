package dev.einsjannis.compiler.llvm

class Module private constructor(
	val function: MutableList<Function> = mutableListOf(),
	val stringConst: MutableList<IRElement.Named.Global.StringConst> = mutableListOf()
) : IRElement {

	companion object {

		fun new() : Module = Module()

	}

	fun addFunctionDeclaration(name: String, returnType: Type): Function.FunctionDeclaration =
		Function.FunctionDeclaration(name, returnType).also { function.add(it) }

	fun addFunction(name: String, returnType: Type): Function.FunctionImplementation =
		Function.FunctionImplementation(name, returnType).also { function.add(it) }

	fun addStringConst(name: String, string: String): Variable =
		IRElement.Named.Global.StringConst(name, string).also { stringConst.add(it) }

	fun inlinePrimitive(primitiveValue: PrimitiveValue): Variable = object : IRElement.Named {
		override val name: String = primitiveValue.asString()
		override val type: Type = primitiveValue.type
		override fun generateNameIR(): String = name
		override fun generateIR(): String = name
	}

	fun getFunctionByName(name: String): Function? = function.find { it.name == name }

	override fun generateIR(): String {
		val builder = StringBuilder()
		stringConst.forEach { builder.append(it.generateIR()); builder.append("\n") }
		function.forEach { builder.append(it.generateIR()); builder.append("\n") }
		return builder.toString()
	}

}
