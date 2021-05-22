package dev.einsjannis.lang.compiler.generator

import dev.einsjannis.compiler.llvm.Module
import dev.einsjannis.compiler.llvm.Type
import dev.einsjannis.lang.compiler.generator.FunctionGeneration.Companion.generateFunction
import dev.einsjannis.lang.compiler.ir.*

object CodeGeneration {

	fun generate(name: String, definitionScope: DefinitionScope): Module {
		val module = generateModule(name, definitionScope)
		definitionScope.children.forEach { module.generateDefinition(it) }
		TODO()
	}

	fun generateModule(name: String, definitionScope: DefinitionScope): Module {
		val module = Module.new(name)
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

	fun Module.type(returnType: ReturnType): Type {
		return typeByName(returnType.typeDefinition.name)
	}

}
