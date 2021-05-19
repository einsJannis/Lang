package dev.einsjannis.lang.compiler

import dev.einsjannis.compiler.llvm.Module
import dev.einsjannis.lang.compiler.ir.Definition
import dev.einsjannis.lang.compiler.ir.FunctionDefinition
import dev.einsjannis.lang.compiler.ir.StructDefinition

object CodeGeneration {

	fun generateModule(name: String, definitionScope: dev.einsjannis.lang.compiler.ir.DefinitionScope): Module {
		val module = Module.new(name)
		definitionScope.children.forEach { module.generateDefinition(it) }
		TODO()
	}

	fun Module.generateDefinition(definition: Definition) = when (definition) {
		is FunctionDefinition -> generateFunction(definition)
		is StructDefinition -> generateStruct(definition)
		else -> TODO()
	}

	fun Module.generateFunction(functionDef: FunctionDefinition) {
		TODO()
	}

	fun Module.generateStruct(structDefinition: StructDefinition) {
		addStruct(structDefinition.name)
		TODO()
	}

}
