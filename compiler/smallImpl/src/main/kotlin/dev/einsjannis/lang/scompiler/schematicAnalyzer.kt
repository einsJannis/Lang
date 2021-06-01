package dev.einsjannis.lang.scompiler

import kotlin.String

fun List<Definition>.analyse() {
	val types: MutableList<TypeDef> = mutableListOf()
	val functions: MutableList<FunctionDef> = mutableListOf()
	forEach { analyseDef(it, types, functions) }
}

fun analyseDef(def: Definition, types: MutableList<TypeDef>, functions: MutableList<FunctionDef>) = when (def) {
	is FunctionImplDef -> analyseFunction(def, types, functions)
	else -> throw Exception("huh?")
}

fun analyseFunction(functionDef: FunctionImplDef, types: MutableList<TypeDef>, functions: MutableList<FunctionDef>) {
	val templateTypes: MutableList<String> = mutableListOf()
	val templateConsts: MutableList<VariableDef> = mutableListOf()
	val variableDefs: MutableList<VariableDef> = mutableListOf()
	analyseTemplate(functionDef.templateArgumentDefs, types, templateTypes, templateConsts, variableDefs)
	analyseArgumentDefs(functionDef.argumentDefs, types, templateTypes, variableDefs)
	analyseReturnType(functionDef.returnType, types, templateTypes)
	analyseCode(functionDef.code, types, functions, templateTypes, templateConsts, variableDefs)
}

fun analyseTemplate(
	templateArgDefs: List<TemplateArgumentDef>,
	types: MutableList<TypeDef>,
	templateTypes: MutableList<String>,
	templateConsts: MutableList<VariableDef>,
	variableDefs: MutableList<VariableDef>
) = templateArgDefs.forEach { analyseTemplateArgument(it, types, templateTypes, templateConsts, variableDefs) }

fun analyseTemplateArgument(
	templateArgDef: TemplateArgumentDef,
	types: MutableList<TypeDef>,
	templateTypes: MutableList<String>,
	templateConsts: MutableList<VariableDef>,
	variableDefs: MutableList<VariableDef>
) = when (templateArgDef) {
	is TemplateArgumentDef.Type -> analyseTypeTemplArg(templateArgDef, templateTypes)
	is TemplateArgumentDef.Const ->
		analyseConstTemplArg(templateArgDef, types, templateTypes, templateConsts, variableDefs)
}

fun analyseConstTemplArg(
	templateArgDef: TemplateArgumentDef.Const,
	types: MutableList<TypeDef>,
	templateTypes: MutableList<String>,
	templateConsts: MutableList<VariableDef>,
	variableDefs: MutableList<VariableDef>
) {
	analyseVariableDef(templateArgDef, types, templateTypes, variableDefs)
	templateConsts.add(templateArgDef)
}

fun analyseTypeTemplArg(
	templateArgDef: TemplateArgumentDef.Type,
	templateTypes: MutableList<String>
) { templateTypes.add(templateArgDef.name) }

fun analyseArgumentDefs(
	arguments: List<VariableDef>,
	types: MutableList<TypeDef>,
	templateTypes: MutableList<String>,
	variableDefs: MutableList<VariableDef>
) = arguments.forEach { analyseVariableDef(it, types, templateTypes, variableDefs) }

fun analyseVariableDef(
	variableDef: VariableDef,
	types: MutableList<TypeDef>,
	templateTypes: MutableList<String>,
	variableDefs: MutableList<VariableDef>
) {
	analyseVariableName(variableDef, types, templateTypes, variableDefs)
	analyseReturnType(variableDef.type, types, templateTypes)
	variableDefs.add(variableDef)
}

fun analyseVariableName(
	variableDef: VariableDef,
	types: MutableList<TypeDef>,
	templateTypes: MutableList<String>,
	variableDefs: List<VariableDef>
) {
	if (
		types.any { it.name == variableDef.name } &&
		templateTypes.any { it == variableDef.name } &&
		variableDefs.any { it.name == variableDef.name }
	) throw Exception("Name is already in use")
}

fun analyseReturnType(
	returnType: ReturnType,
	types: MutableList<TypeDef>,
	templateTypes: MutableList<String>
) {
	if (returnType !is ReturnTypeImpl) throw Exception("huh?")
	val templateType = templateTypes.find { it == returnType.name }
	val typeDef = types.find { it.name == returnType.name }
	if (templateType != null)
		returnType._typeDef = TODO()
	else if (typeDef != null)
		returnType._typeDef = typeDef
	else throw Exception("No TypeDef found for ${returnType.name}")
}

fun analyseCode(
	code: Code,
	types: MutableList<TypeDef>,
	functions: MutableList<FunctionDef>,
	templateTypes: MutableList<String>,
	templateConsts: MutableList<VariableDef>,
	variableDefs: MutableList<VariableDef>
) = code.forEach { analyseStatement(it, types, functions, templateTypes, templateConsts, variableDefs) }

fun analyseStatement(
	statement: Statement,
	types: MutableList<TypeDef>,
	functions: MutableList<FunctionDef>,
	templateTypes: MutableList<String>,
	templateConsts: MutableList<VariableDef>,
	variableDefs: MutableList<VariableDef>
) = when (statement) {
	is Expression -> analyseExpression(statement, types, functions, templateTypes, templateConsts, variableDefs)
	is VariableDef -> analyseVariableDefOrImpl(statement, types, functions, templateTypes, templateConsts, variableDefs)
	is Assignment -> analyseAssignment(statement, types, functions, templateTypes, templateConsts, variableDefs)
	is Conditional -> analyseConditional(statement, types, functions, templateTypes, templateConsts, variableDefs)
}

fun analyseExpression(
	expression: Expression,
	types: MutableList<TypeDef>,
	functions: MutableList<FunctionDef>,
	templateTypes: MutableList<String>,
	templateConsts: MutableList<VariableDef>,
	variableDefs: MutableList<VariableDef>
) {
	when (expression) {
		is FunctionCall -> analyseFunctionCall(expression, types, functions, templateTypes, templateConsts, variableDefs)
		is VariableCall -> analyseVariableCall(expression, variableDefs)
		is Primitive -> return
	}
	analyseReturnType(expression.returnType, types, templateTypes)
}

fun analyseFunctionCall(
	functionCall: FunctionCall,
	types: MutableList<TypeDef>,
	functions: MutableList<FunctionDef>,
	templateTypes: MutableList<String>,
	templateConsts: MutableList<VariableDef>,
	variableDefs: MutableList<VariableDef>
) {
	if (functionCall !is FunctionCallImpl) throw Exception("huh?")
	analyseArguments(functionCall.arguments, types, functions, templateTypes, templateConsts, variableDefs)
	functionCall._funDef = functions.find {
		it.name == functionCall.name &&
			it.argumentDefs.filterIndexed { index, variableDef ->
				functionCall.arguments[index].returnType != variableDef.type
			}.isNotEmpty()
	}
	TODO()
}

fun analyseArguments(
	arguments: List<Expression>,
	types: MutableList<TypeDef>,
	functions: MutableList<FunctionDef>,
	templateTypes: MutableList<String>,
	templateConsts: MutableList<VariableDef>,
	variableDefs: MutableList<VariableDef>
) = arguments.forEach { analyseExpression(it, types, functions, templateTypes, templateConsts, variableDefs) }

fun analyseVariableCall(variableCall: VariableCall, variableDefs: MutableList<VariableDef>) {
	if (variableCall !is VariableCallImpl) throw Exception("huh?")
	if (variableCall.parent != null) {
		analyseVariableCall(variableCall, variableDefs)
		TODO()
	} else {
		variableDefs.find { it.name == variableCall.name }
	}
}

fun analyseVariableDefOrImpl(
	variableDef: VariableDef,
	types: MutableList<TypeDef>,
	functions: MutableList<FunctionDef>,
	templateTypes: MutableList<String>,
	templateConsts: MutableList<VariableDef>,
	variableDefs: MutableList<VariableDef>
) {
	analyseVariableDef(variableDef, types, templateTypes, variableDefs)
	if (variableDef is VariableImpl) {
		analyseExpression(variableDef.initializer, types, functions, templateTypes, templateConsts, variableDefs)
		if (variableDef.type != variableDef.initializer.returnType) throw Exception("wrong type")
	}
}

fun analyseAssignment(
	assignment: Assignment,
	types: MutableList<TypeDef>,
	functions: MutableList<FunctionDef>,
	templateTypes: MutableList<String>,
	templateConsts: MutableList<VariableDef>,
	variableDefs: MutableList<VariableDef>
) {
	analyseVariableCall(assignment.varCall, variableDefs)
	analyseExpression(assignment.initializer, types, functions, templateTypes, templateConsts, variableDefs)
	if (assignment.varCall.returnType != assignment.initializer.returnType) throw Exception("wrong type")
}

fun analyseConditional(
	conditional: Conditional,
	types: MutableList<TypeDef>,
	functions: MutableList<FunctionDef>,
	templateTypes: MutableList<String>,
	templateConsts: MutableList<VariableDef>,
	variableDefs: MutableList<VariableDef>
) {
	analyseExpression(conditional.condition, types, functions, templateTypes, templateConsts, variableDefs)
	if (conditional.condition.returnType.typeDef != Type.Boolean) throw Exception("wrong type (searched for boolean)")
	analyseCode(conditional.code, types, functions, templateTypes, templateConsts, variableDefs)
	conditional.other?.let { analyseConditional(it, types, functions, templateTypes, templateConsts, variableDefs) }
}
