package dev.einsjannis.lang.compiler

fun List<FunctionImplementation>.analyse(): List<FunctionImplementation> =
	SemanticAnalyzer(this).apply { analyse() }.functionImplementations

class SemanticAnalyzer(val functionImplementations: List<FunctionImplementation>) {

	private val functions: MutableList<Function> = Functions.all.toMutableList()
	private val types: List<Type> get() = Types.all

	fun analyse() {
		functionImplementations.forEach {
			analyseID(it, functions)
			functions += it
		}
		functionImplementations.forEach { analyseFunImpl(it) }
	}

	private fun analyseID(named: Named, list: List<Named>) {
		if (list.any { it.id() == named.id() }) throw RuntimeException()
	}

	private fun analyseFunImpl(function: FunctionImplementation) =
		SemanticFunctionAnalyzer(function).apply { analyse() }

	private inner class SemanticFunctionAnalyzer(val function: FunctionImplementation) {

		private val variables: MutableList<Variable> = mutableListOf()

		fun analyse() {
			analyseReturnType(function.returnType)
			analyseArguments(function.arguments)
			analyseCode(function.code)
		}

		private fun analyseArguments(arguments: List<Variable>) = arguments.forEach { analyseVariable(it) }

		private fun analyseVariable(variable: Variable) {
			analyseID(variable, variables)
			analyseReturnType(variable.returnType)
			variables += variable
		}

		private fun analyseReturnType(returnType: Type) {
			if (types.none { it.id() == returnType.id() }) throw RuntimeException()
		}

		private fun analyseCode(code: List<Statement>) = code.forEach { analyseStatement(it) }

		private fun analyseStatement(statement: Statement) = when (statement) {
			is AssignmentStatement -> analyseAssignment(statement)
			is ConditionStatement -> analyseCondition(statement)
			is VariableDef -> analyseVariable(statement)
			is Expression -> analyseExpression(statement)
			is ReturnStatement -> analyseReturnStatement(statement)
		}

		private fun analyseReturnStatement(returnStatement: ReturnStatement) {
			analyseExpression(returnStatement.expression)
			if (function.returnType.id() != function.returnType.id()) throw RuntimeException()
		}

		private fun analyseAssignment(assignmentStatement: AssignmentStatement) {
			analyseVarCall(assignmentStatement.variableCall)
			analyseExpression(assignmentStatement.expression)
		}

		private fun analyseCondition(conditionStatement: ConditionStatement) {
			analyseExpression(conditionStatement.condition)
			analyseCode(conditionStatement.code)
			conditionStatement.other?.also { analyseCondition(it) }
		}

		private fun analyseExpression(expression: Expression) = when (expression) {
			is FunctionCall -> analyseFunCall(expression)
			is VariableCall -> analyseVarCall(expression)
			is Primitive -> {}
		}

		private fun analyseFunCall(functionCall: FunctionCall) {
			if (functionCall !is FunctionCallImpl) throw RuntimeException("tf?")
			functionCall.arguments.forEach { analyseExpression(it) }
			functionCall.function = functions.find {
				it.id() == functionCall.id()
			} ?: throw RuntimeException()
			functionCall.arguments.zip(functionCall.function.arguments).any { it.first.returnType.id() == it.second.returnType.id() }
		}

		private fun analyseVarCall(variableCall: VariableCall) {
			if (variableCall !is VariableCallImpl) throw RuntimeException("tf?")
			variableCall.variable = variables.find { it.name == variableCall.variableName } ?: throw RuntimeException()
		}

	}

}
