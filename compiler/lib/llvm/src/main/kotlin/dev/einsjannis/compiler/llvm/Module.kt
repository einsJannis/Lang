package dev.einsjannis.compiler.llvm

import com.sun.jna.NativeLibrary
import org.sosy_lab.llvm_j.binding.LLVMLibrary
import java.io.File

class Module private constructor(private val ref: LLVMLibrary.LLVMModuleRef) {

	internal val contextRef get() = LLVMLibrary.LLVMGetModuleContext(ref)

	companion object {

		init {
			val path = File("lib").absolutePath
			NativeLibrary.addSearchPath("LLVM-6.0", path)
			println(path)
		}

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

	fun typeByName(name: String): Type = Type(LLVMLibrary.LLVMGetTypeByName(ref, name))


}
