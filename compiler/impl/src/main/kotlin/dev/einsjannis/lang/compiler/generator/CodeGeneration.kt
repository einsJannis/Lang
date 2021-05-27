package dev.einsjannis.lang.compiler.generator

import dev.einsjannis.compiler.llvm.Module
import dev.einsjannis.compiler.llvm.Type
import dev.einsjannis.lang.compiler.generator.FunctionGeneration.Companion.generateFunction
import dev.einsjannis.lang.compiler.ir.*
import dev.einsjannis.lang.compiler.ir.builtin.Types

object CodeGeneration {

	fun generate(name: String, definitionScope: DefinitionScope): Module {
		val module = generateModule(name, definitionScope)
		definitionScope.children.forEach { module.generateDefinition(it) }
		TODO()
	}

	fun generateModule(name: String, definitionScope: DefinitionScope): Module {
		val module = Module.new()
		definitionScope.children.forEach { module.generateDefinition(it) }
		return module
	}

	fun Module.generateDefinition(definition: Definition) = when (definition) {
		is FunctionDefinition -> generateFunction(definition)
		is StructDefinition -> generateStruct(definition)
		else -> TODO()
	}

	fun Module.generateStruct(structDefinition: StructDefinition) {
		addStruct(structDefinition.name)
	}

	fun Module.type(returnType: ReturnType): Type = when(returnType.typeDefinition) {
		Types.Unit -> Type.BuiltIn.VoidType
		Types.Byte -> Type.BuiltIn.Number.I8
		Types.Integer -> Type.BuiltIn.Number.I32
		Types.Long -> Type.BuiltIn.Number.I64
		Types.TypeMeta -> TODO()
		else -> getStructByName(returnType.typeDefinition.name)
			?: throw Exception("unknown type: ${returnType.typeDefinition.name}")
	}

}
