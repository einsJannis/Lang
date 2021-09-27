package dev.einsjannis.lang.compiler

import dev.einsjannis.compiler.llvm.*
import dev.einsjannis.compiler.llvm.Type
import dev.einsjannis.compiler.llvm.Function.FunctionImplementation as LlvmFunctionImplementation
import dev.einsjannis.compiler.llvm.Function as LlvmFunction
import dev.einsjannis.compiler.llvm.Variable as LlvmVariable

fun generate(functions: List<FunctionImplementation>): String {
	val module = Module.new()
	module.initialize()
	functions.forEach { module.addFunction(it) }
	return module.generateIR()
}

fun Module.function(function: Function): LlvmFunction =
	getFunctionByName(function.id()) ?: throw IllegalStateException()

fun primitive(type: Type, asString: () -> String) = object : PrimitiveValue() {
	override val type: Type = type
	override fun asString(): String = asString()
}

private fun Module.addFunction(function: FunctionImplementation) {
	val generator = FunctionGenerator(this, addFunction(function.id(), type(function.returnType)))
	function.arguments.forEach { generator.addArgument(it) }
	function.code.forEach { generator.addStatement(it) }
}

class FunctionGenerator(private val module: Module, private val function: LlvmFunctionImplementation) {

	fun addArgument(variable: Variable) =
		function.addArgument(variable.name, module.type(variable.returnType))

	fun addStatement(statement: Statement) { when (statement) {
		is ConditionStatement -> addConditionStatement(statement)
		is AssignmentStatement -> addAssignmentStatement(statement)
		is VariableDef -> addVariableDef(statement)
		is ReturnStatement -> addReturnStatement(statement)
		is Expression -> addExpression(statement)
	} }

	private fun addReturnStatement(returnStatement: ReturnStatement) =
		function.addReturnCall(addExpression(returnStatement.expression))

	private fun addConditionStatement(conditionStatement: ConditionStatement, endIfLabelName: String? = null) {
		val condition = addExpression(conditionStatement.condition)
		val conditionRes = function.addIcmpCall(
			Code.IcmpCall.Operator.EQ,
			function.addLoadCall(condition, "\$conditionv${tmpName()}"),
			function.addPrimitive(primitive(condition.type) { "0" }),
			tmpName()
		)
		val ifLabelName = "\$if" + tmpName()
		val afterLabelName = endIfLabelName ?: ("\$endif" + tmpName())
		val otherBranch = conditionStatement.other
		if (otherBranch != null) {
			val elseLabelName = "\$else" + tmpName()
			function.addBrCall(conditionRes, ifLabelName, elseLabelName)
			function.addLabel(ifLabelName)
			conditionStatement.code.forEach { addStatement(it) }
			function.addBrCall(afterLabelName)
			function.addLabel(elseLabelName)
			addConditionStatement(otherBranch, afterLabelName)
		} else {
			function.addBrCall(conditionRes, ifLabelName, afterLabelName)
			function.addLabel(ifLabelName)
			conditionStatement.code.forEach { addStatement(it) }
			function.addBrCall(afterLabelName)
			function.addLabel(afterLabelName)
		}
	}

	private fun addAssignmentStatement(assignmentStatement: AssignmentStatement) =
		function.addStoreCall(
			function.addLoadCall(addExpression(assignmentStatement.expression, tmpName()), tmpName()),
			variable(assignmentStatement.variableCall)
		)

	private fun addMallocCall(type: dev.einsjannis.lang.compiler.Type, name: String) =
		function.addBitCast(function.addFunctionCall(
			module.getFunctionByName("malloc")!!,
			listOf(function.addPrimitive(primitive(Type.BuiltIn.Number.Integer(64)) { "${module.size(type)}" })),
			"\$malloc$name"
		), module.type(type), name)

	private fun addVariableDef(variableDef: VariableDef) =
		addMallocCall(variableDef.returnType, variableDef.name)

	private fun addExpression(expression: Expression, varName: String? = null): LlvmVariable = when (expression) {
		is FunctionCall -> addFunctionCall(expression, varName)
		is VariableCall -> addVariableCall(expression, varName)
		is Primitive -> addPrimitive(expression, varName)
	}

	private fun addVariableCall(variableCall: VariableCall, varName: String? = null): LlvmVariable =
		if (varName == null) variable(variableCall) else function.addVarAlias(variable(variableCall), varName)

	private fun addFunctionCall(functionCall: FunctionCall, varName: String? = null): LlvmVariable {
		val arguments = functionCall.arguments.map { addExpression(it) }
		return function.addFunctionCall(module.function(functionCall.function), arguments, varName ?: tmpName())
	}

	private fun addPrimitive(primitive: Primitive, varName: String? = null): LlvmVariable {
		val primitiveValue = function.addPrimitive(primitive.toValue())
		val variable = addMallocCall(primitive.returnType, varName ?: tmpName())
		function.addStoreCall(primitiveValue, variable)
		return variable
	}

	fun Primitive.toValue(): PrimitiveValue {
		fun primitiveValue(string: String) = object : PrimitiveValue() {
			override val type = module.type(this@toValue.returnType).child
			override fun asString(): String = string
		}
		return when (this) {
			is Boolean -> primitiveValue(if (this.value) "0" else "1")
			is Character -> primitiveValue("${this.value.code}")
			is Number -> primitiveValue("${this.value}")
		}
	}

	@Suppress("IMPLICIT_CAST_TO_ANY")
	private fun variable(variableCall: VariableCall): LlvmVariable =
		(((function.code.find { it is LlvmVariable && it.name == variableCall.variable.name } as? LlvmVariable)
			?: function.arguments.findLast { it.name == variableCall.variable.name }) ?: throw Exception("huh?"))

	private var tempCount: Int = 0

	private fun tmpName() = "\$tmp$tempCount".also { tempCount++ }

}
