package dev.einsjannis.compiler.llvm

import org.sosy_lab.llvm_j.binding.LLVMLibrary

class Function internal constructor(private val ref: LLVMLibrary.LLVMValueRef) {

	val module: Module get() = TODO()

	fun addArgument(name: String, type: Type) {
		TODO()
	}

	fun addFunctionCall(function: Function, arguments: List<Variable>, returnName: String): Variable {
		TODO()
	}

	fun addStructVariableCall(variable: Variable, fieldName: String, returnName: String): Variable {
		TODO()
	}

	fun addVariable(old: Variable, newName: String): Variable {
		TODO()
	}

	fun addReturnStatement(returnVar: Variable) {
		TODO()
	}

}
