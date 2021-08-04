package dev.einsjannis.compiler.llvm

class Module private constructor(
	val function: MutableList<Function> = mutableListOf()
) : IRElement {

	companion object {

		fun new() : Module = Module()

	}

	fun addFunctionDeclaration(name: String, returnType: Type): Function.FunctionDeclaration = Function.FunctionDeclaration(name, returnType).also { function.add(it) }

	fun addFunction(name: String, returnType: Type): Function.FunctionImplementation = Function.FunctionImplementation(name, returnType).also { function.add(it) }

	fun getFunctionByName(name: String): Function? = function.find { it.name == name }

	override fun generateIR(): String {
		val builder = StringBuilder()
		function.forEach { builder.append(it.generateIR()) }
		return builder.toString()
	}

}
