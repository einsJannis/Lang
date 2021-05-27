package dev.einsjannis.compiler.llvm

import com.sun.jna.NativeLibrary
import java.io.File

class Module private constructor(
	val function: MutableList<Function> = mutableListOf(),
	val struct: MutableList<Type.StructType> = mutableListOf()
) {

	companion object {

		fun new() : Module = Module()

	}

	fun addFunction(name: String, returnType: Type): Function = Function(name, returnType).also { function.add(it) }

	fun addStruct(name: String): Type.StructType = Type.StructType(name).also { struct.add(it) }

	fun getStructByName(name: String): Type.StructType? = struct.find { it.name == name }

	fun getFunctionByName(name: String): Function? = function.find { it.name == name }

}
