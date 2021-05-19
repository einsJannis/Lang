package dev.einsjannis.compiler.llvm

import org.sosy_lab.llvm_j.binding.LLVMLibrary

class Module private constructor(private val ref: LLVMLibrary.LLVMModuleRef) {

	internal val contextRef get() = LLVMLibrary.LLVMGetModuleContext(ref)

	companion object {

		fun new(name: String) : Module = Module(LLVMLibrary.LLVMModuleCreateWithName(name))

	}

	fun addFunction(name: String, returnType: Type): Function {
		val function = LLVMLibrary.LLVMAddFunction(ref, name, returnType.ref)

		return Function(function)
	}

	fun addStruct(name: String): Type {
		val type = LLVMLibrary.LLVMStructCreateNamed(contextRef, name)

		return Type(type)
	}

}
