package dev.einsjannis.lang.compiler

fun analyse(functions: List<FunctionImplementation>) {
	val registeredFunctions = Functions.all.toMutableList()
	fun analyseFunction(function: FunctionImplementation) = FunctionAnalyzer(function, registeredFunctions).analyse()
	functions.forEach(::analyseFunction)
}

private class FunctionAnalyzer(private val function: FunctionImplementation, private val registeredFunctions: List<Function>) {

	private val registeredDeclarations: MutableList<Declaration> = Functions.all.toMutableList()

	private fun analyseReturnType(type: Type) {
		if (!Types.all.any { it.name == type.name }) throw UnknownRefException(type.name, "type")
	}
	private fun analyseVariable(variable: Variable) {
		registeredDeclarations.find { it.equalsExactly(variable) }?.also { throw DeclarationClash(variable, it) }
		analyseReturnType(variable.returnType)
		registeredDeclarations += variable
	}
	private fun analyseFunctionCall(functionCall: FunctionCall) {
		if (functionCall !is FunctionCallImpl) throw IllegalStateException()
		functionCall.arguments.forEach(::analyseExpression)
		functionCall.function = registeredDeclarations.find { it is Function && functionCall.id() == it.id() }
			as? Function ?: throw UnknownRefException(functionCall.name, "function")
	}
	private fun analyseVariableCall(variableCall: VariableCall) {
		if (variableCall !is VariableCallImpl) throw IllegalStateException()
		variableCall.variable = registeredDeclarations.find { it is Variable && variableCall.name == it.name }
			as? Variable ?: throw UnknownRefException(variableCall.name, "variable")
	}
	private fun analyseExpression(expression: Expression) = when (expression) {
		is FunctionCall -> analyseFunctionCall(expression)
		is VariableCall -> analyseVariableCall(expression)
		is Primitive -> Unit
	}
	private fun analyseAssignment(assignment: Assignment) {
		if (assignment !is AssignmentImpl) throw IllegalStateException()
		assignment.variable = registeredDeclarations.find { it is Variable && assignment.name == it.name }
			as? Variable ?: throw UnknownRefException(assignment.name, "variable")
	}
	private fun analyseReturnStatement(returnStatement: ReturnStatement) {
		analyseExpression(returnStatement.expression)
		if (!function.returnType.equalsExactly(returnStatement.expression.returnType))
			throw ReturnTypeMissMatch(function, returnStatement)
	}
	private fun analyseStatement(statement: Statement) = when (statement) {
		is Variable        -> analyseVariable(statement)
		is Expression      -> analyseExpression(statement)
		is Assignment      -> analyseAssignment(statement)
		is ReturnStatement -> analyseReturnStatement(statement)
	}
	private fun analyseCode(code: List<Statement>) = code.forEach(::analyseStatement)

	fun analyse() {
		registeredFunctions.find { it.equalsExactly(function) }?.also { throw DeclarationClash(function, it) }
		registeredDeclarations += registeredFunctions
		registeredDeclarations += function
		function.arguments.forEach(::analyseVariable)
		analyseReturnType(function.returnType)
		analyseCode(function.code)
	}
}
