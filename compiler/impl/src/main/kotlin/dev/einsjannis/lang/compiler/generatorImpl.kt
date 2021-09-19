package dev.einsjannis.lang.compiler

import dev.einsjannis.compiler.llvm.*
import dev.einsjannis.compiler.llvm.Type
import dev.einsjannis.compiler.llvm.Function.FunctionImplementation as LlvmFunctionImplementation
import dev.einsjannis.compiler.llvm.Function as LlvmFunction
import dev.einsjannis.compiler.llvm.Variable as LlvmVariable

fun generate(functions: List<FunctionImplementation>): kotlin.String {
	val module = Module.new()
	module.initialize()
	functions.forEach { module.addFunction(it) }
	return module.generateIR()
}

@Suppress("UNUSED")
fun Module.type(type: dev.einsjannis.lang.compiler.Type): Type.BuiltIn.PointerType<Type> = when {
	type.id() == Types.Byte.id() -> Type.BuiltIn.Number.Integer(8).ptr()
	type.id() == Types.Long.id() -> Type.BuiltIn.Number.Integer(64).ptr()
	type.id() == Types.Pointer.id() -> Type.BuiltIn.VoidType.ptr()
	type.id() == Types.String.id() -> Type.BuiltIn.Number.Integer(8).ptr().ptr()
	else -> throw IllegalStateException()
}

fun Module.function(function: Function): LlvmFunction =
	getFunctionByName(function.id()) ?: throw IllegalStateException()

private fun Module.addPrintfImport() =
	addFunctionDeclaration("printf", Type.BuiltIn.Number.Integer(32))
private fun Module.addScanfImport() =
	addFunctionDeclaration("scanf", Type.BuiltIn.Number.Integer(32))
private fun Module.initialize() {
	addPrintfImport()
	addScanfImport()
	addPrintlnFunction()
	addScanlnFunction()
	addLongAdditionFunction()
	addByteAdditionFunction()
	addLongSubtractionFunction()
	addByteSubtractionFunction()
	addLongMultiplication()
	addByteMultiplication()
	addLongDivision()
	addByteDivision()
	addLongRemainder()
	addByteRemainder()
	addLongShiftLeft()
	addByteShiftLeft()
	addLongShiftRight()
	addByteShiftRight()
	addLongAnd()
	addByteAnd()
	addLongOr()
	addByteOr()
	addLongXOr()
	addByteXOr()
}
private fun Module.addPrintlnFunction() = functionImpl("println\$String", type(Types.Unit)) {
	val textPtr = addArgument("textPtr", Type.BuiltIn.Number.Integer(8).ptr())
	val text = addLoadCall("text", textPtr)
	val primitive = addPrimitive(primitive(Type.BuiltIn.Number.Integer(8).ptr()) {"\"%s\""}, "primitive")
	addFunctionCall(getFunctionByName("printf")!!, listOf(primitive, text), "ignored")
}
private fun Module.addScanlnFunction() {
	//TODO
}
private fun Module.addLongAdditionFunction() = longOperation("add", LlvmFunction.FunctionImplementation::addAddCall)
private fun Module.addByteAdditionFunction() = byteOperation("add", LlvmFunction.FunctionImplementation::addAddCall)
private fun Module.addLongSubtractionFunction() =
	longOperation("sub", LlvmFunction.FunctionImplementation::addSubCall)
private fun Module.addByteSubtractionFunction() =
	byteOperation("sub", LlvmFunction.FunctionImplementation::addSubCall)
private fun Module.addLongMultiplication() =
	longOperation("mul", LlvmFunction.FunctionImplementation::addMulCall)
private fun Module.addByteMultiplication() =
	byteOperation("mul", LlvmFunction.FunctionImplementation::addMulCall)
private fun Module.addLongDivision() =
	longOperation("div", LlvmFunction.FunctionImplementation::addSDivCall)
private fun Module.addByteDivision() =
	byteOperation("div", LlvmFunction.FunctionImplementation::addSDivCall)
private fun Module.addLongRemainder() =
	longOperation("rem", LlvmFunction.FunctionImplementation::addSRemCall)
private fun Module.addByteRemainder() =
	byteOperation("rem", LlvmFunction.FunctionImplementation::addSRemCall)
private fun Module.addLongShiftLeft() =
	longOperation("shl", LlvmFunction.FunctionImplementation::addShlCall)
private fun Module.addByteShiftLeft() =
	byteOperation("shl", LlvmFunction.FunctionImplementation::addShlCall)
private fun Module.addLongShiftRight() =
	longOperation("shr", LlvmFunction.FunctionImplementation::addLShrCall)
private fun Module.addByteShiftRight() =
	byteOperation("shr", LlvmFunction.FunctionImplementation::addLShrCall)
private fun Module.addLongAnd() =
	longOperation("and", LlvmFunction.FunctionImplementation::addAndCall)
private fun Module.addByteAnd() =
	byteOperation("and", LlvmFunction.FunctionImplementation::addAndCall)
private fun Module.addLongOr() =
	longOperation("or", LlvmFunction.FunctionImplementation::addOrCall)
private fun Module.addByteOr() =
	byteOperation("or", LlvmFunction.FunctionImplementation::addOrCall)
private fun Module.addLongXOr() =
	longOperation("xor", LlvmFunction.FunctionImplementation::addXOrCall)
private fun Module.addByteXOr() =
	byteOperation("xor", LlvmFunction.FunctionImplementation::addXOrCall)

typealias OperationFunction = LlvmFunction.FunctionImplementation.(LlvmVariable, LlvmVariable, kotlin.String) -> LlvmVariable

private fun Module.longOperation(operationName: kotlin.String, operationF: OperationFunction) =
	operation("Long", type(Types.Long), operationName, operationF)

private fun Module.byteOperation(operationName: kotlin.String, operationF: OperationFunction) =
	operation("Byte", type(Types.Byte), operationName, operationF)

private fun Module.operation(
	typeName: kotlin.String,
	type: Type,
	operationName: kotlin.String,
	operation: OperationFunction
) = functionImpl("$operationName\$$typeName\$$typeName", type) {
	val (arg0, arg1) = args(2, type)
	addReturnCall(operation(arg0, arg1, "result"))
}

private fun LlvmFunction.args(amount: Int, type: Type, name: kotlin.String = "arg") =
	(0 until amount).map { addArgument(name + it, type) }

private fun Module.functionImpl(name: kotlin.String, returnType: Type, block: LlvmFunction.FunctionImplementation.() -> Unit) =
	addFunction(name, returnType).block()
private fun primitive(type: Type, asString: () -> kotlin.String) = object : PrimitiveValue() {
	override val type: Type = type
	override fun asString(): kotlin.String = asString()
}

private fun Module.addFunction(function: FunctionImplementation) {
	val f = addFunction(function.id(), type(function.returnType))
	val generator = FunctionGenerator(this, f)
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

	private fun addConditionStatement(conditionStatement: ConditionStatement) {
		val condition = addExpression(conditionStatement.condition)
		val conditionRes = function.addIcmpCall(
			IcmpOperator.EQ,
			condition,
			function.addPrimitive(
				object : PrimitiveValue() {
					override val type: Type = condition.type
					override fun asString(): kotlin.String = "0"
				},
				tmpName()
			),
			tmpName()
		)
		val ifLabelName = "if" + tmpName()
		val elseLabelName = "if" + tmpName()
		function.addBrCall(conditionRes, ifLabelName, elseLabelName)
		function.addLabel(ifLabelName)
		conditionStatement.code.forEach { addStatement(it) }
		function.addLabel(elseLabelName)
		conditionStatement.other?.let { addConditionStatement(it) }
	}

	private fun addAssignmentStatement(assignmentStatement: AssignmentStatement) =
		addExpression(assignmentStatement.expression, assignmentStatement.variableCall.variable.name)

	private fun addVariableDef(variableDef: VariableDef) =
		function.addAllocationCall(module.type(variableDef.returnType), variableDef.name)

	private fun addExpression(expression: Expression, varName: kotlin.String? = null): LlvmVariable = when (expression) {
		is FunctionCall -> addFunctionCall(expression, varName)
		is VariableCall -> addVariableCall(expression, varName)
		is Primitive -> addPrimitive(expression, varName)
	}

	private fun addVariableCall(variableCall: VariableCall, varName: kotlin.String? = null): LlvmVariable =
		if (varName == null) variable(variableCall) else function.addVarAlias(variable(variableCall), varName)

	private fun addFunctionCall(functionCall: FunctionCall, varName: kotlin.String? = null): LlvmVariable {
		val arguments = functionCall.arguments.map { addExpression(it) }
		return function.addFunctionCall(module.function(functionCall.function), arguments, varName ?: tmpName())
	}

	private fun addPrimitive(primitive: Primitive, varName: kotlin.String? = null): LlvmVariable {
		val primitiveValue = function.addPrimitive(primitive.toValue(), tmpName())
		val variable = function.addAllocationCall(primitiveValue.type.ptr(), varName ?: tmpName())
		function.addStoreCall(primitiveValue, variable)
		return variable
	}

	fun Primitive.toValue(): PrimitiveValue {
		fun primitiveValue(string: kotlin.String) = object : PrimitiveValue() {
			override val type = module.type(this@toValue.returnType).child
			override fun asString(): kotlin.String = string
		}
		return when (this) {
			is Boolean -> primitiveValue(if (this.value) "0" else "1")
			is Character -> primitiveValue("${this.value.code}")
			is Number -> primitiveValue("${this.value}")
			is String -> primitiveValue("\"${this.value}\"")
		}
	}

	@Suppress("IMPLICIT_CAST_TO_ANY")
	private fun variable(variableCall: VariableCall): LlvmVariable =
		(((function.code.find { it is LlvmVariable && it.name == variableCall.variable.name }
			as? LlvmVariable) ?: function.arguments.find { it.name == variableCall.variable.name }) ?: throw Exception("huh?"))

	private var tempCount: Int = 0

	private fun tmpName() = "\$tmp$tempCount".also { tempCount++ }

}
