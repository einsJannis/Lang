package dev.einsjannis.lang.compiler.generator

import dev.einsjannis.compiler.llvm.Function
import dev.einsjannis.compiler.llvm.Module
import dev.einsjannis.compiler.llvm.NamedIRElement
import dev.einsjannis.lang.compiler.generator.CodeGeneration.type
import dev.einsjannis.lang.compiler.ir.*

class FunctionGeneration(val function: Function) {

	var tempCount: Int = 0

	fun addArgument(argumentDefinition: ArgumentDefinition) {
		function.addArgument(argumentDefinition.name, TODO()/*function.module.type(argumentDefinition.returnType)*/)
	}

	fun addCode(code: Code): Unit {
		when (code) {
			is Expression -> addExpression(code)
			is VariableDefinition -> addVariableDef(code)
			is AssignmentStatement -> addAssignment(code)
			is ReturnStatement -> addReturnStatement(code)
			is ConditionStatement -> TODO()
			else -> {}
		}
	}

	fun addExpression(expression: Expression, varName: String? = null): NamedIRElement = when (expression) {
		is FunctionCall -> addFunctionCall(expression)
		is VariableCall -> addVariableCall(expression)
		else -> TODO()
	}

	fun addFunctionCall(functionCall: FunctionCall, varName: String? = null): NamedIRElement {
		val args = functionCall.arguments.children.map { addExpression(it) }
		val varName = varName ?: generateTemp()
		return function.addFunctionCall(TODO()/*function.module.function(functionCall.functionDefinition)*/, args, varName)
	}

	fun addVariableCall(variableCall: VariableCall, varName: String? = null): NamedIRElement {
		if (variableCall.parent == null) {
			val varName = varName ?: generateTemp()
			return function.addVariable(variable(variableCall.variableDefinition.name),varName)
		} else {
			val parent = addExpression(variableCall.parent!!)
			val varName = varName ?: generateTemp()
			return function.addStructVariableCall(parent, variableCall.variableDefinition.name, varName)
		}
	}

	fun generateTemp(): String {
		val result = "temp$tempCount"
		tempCount++
		return result
	}

	fun addVariableDef(variableDefinition: VariableDefinition) {
		if (variableDefinition.initialization != null) {
			addExpression(variableDefinition.initialization!!, variableDefinition.name)
		}
	}

	fun addAssignment(assignmentStatement: AssignmentStatement) {
		addExpression(assignmentStatement.expression, assignmentStatement.variableCall.variableDefinition.name)
	}

	fun addReturnStatement(returnStatement: ReturnStatement) {
		val variable = addExpression(returnStatement.expression)
		function.addReturnStatement(variable)
	}

	fun Module.function(function: FunctionDefinition): Function {
		TODO()
	}

	fun variable(name: String): NamedIRElement {
		TODO()
	}

	companion object {

		fun Module.generateFunction(functionDef: FunctionDefinition) {
			if (functionDef !is FunctionImplementationDefinition) return
			val function = addFunction(functionDef.name, type(functionDef.returnType))
			val generator = FunctionGeneration(function)
			functionDef.arguments.children.forEach { generator.addArgument(it) }
			functionDef.code.children.forEach { generator.addCode(it) }
		}

	}

}
