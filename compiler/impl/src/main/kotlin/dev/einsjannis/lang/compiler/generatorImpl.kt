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
	type.id() == Types.Pointer.id() -> Type.BuiltIn.Number.Integer(8).ptr().ptr()
	//type.id() == Types.String.id() -> Type.BuiltIn.Number.Integer(8).ptr().ptr()
	type.id() == Types.Unit.id() -> Type.BuiltIn.Number.Integer(8).ptr()
	else -> throw IllegalStateException()
}

fun Module.size(type: dev.einsjannis.lang.compiler.Type): kotlin.Long = when {
	type.id() == Types.Byte.id() -> 1L
	type.id() == Types.Long.id() -> 4L
	type.id() == Types.Pointer.id() -> 4L
	type.id() == Types.Unit.id() -> 1L
	else -> throw IllegalStateException()
}

fun Module.function(function: Function): LlvmFunction =
	getFunctionByName(function.id()) ?: throw IllegalStateException()

/*private fun Module.addPrintfImport() =
	addFunctionDeclaration("printf", Type.BuiltIn.Number.Integer(16))*/
/*private fun Module.addScanfImport() =
	addFunctionDeclaration("scanf", Type.BuiltIn.Number.Integer(16))*/
private fun Module.addPutCharImport() =
	addFunctionDeclaration("putchar", Type.BuiltIn.Number.Integer(16)).also {
		it.addArgument("char", Type.BuiltIn.Number.Integer(16))
	}
private fun Module.addGetCharImport() =
	addFunctionDeclaration("getchar", Type.BuiltIn.Number.Integer(16))
private fun Module.initialize() {
	addMallocImport()
	addPutCharImport()
	addPutChar()
	addGetCharImport()
	addGetChar()
	addNativeFreeDef()
	addMangledFreeDef()
	//addPrintfImport()
	//addScanfImport()
	//addPrintlnFunction()
	//addScanlnFunction()
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
	addByteAt()
	addLongAt()
	//addStringAt()
	addPointerAt()
	addPointerOfByte()
	addPointerOfLong()
	//addPointerOfString()
	addPointerOfPointer()
	addEqualByte()
	//addEqualLong()
}
private fun Module.addPutChar() = functionImpl(Functions.putChar) {
	val char = addZext(addLoadCall(arguments[0], "charValue"), Type.BuiltIn.Number.Integer(16), "charInt")
	addFunctionCall(getFunctionByName("putchar")!!, listOf(char), "ignored")
	addReturnCall(IRElement.Named.Null)
}
private fun Module.addGetChar() = functionImpl(Functions.getChar) {
	val char = addFunctionCall(getFunctionByName("getchar")!!, listOf(), "charInt")
	val result = addFunctionCall(getFunctionByName("malloc")!!, listOf(addPrimitive(primitive(Type.BuiltIn.Number.Integer(64)) { "1" })), "result")
	addStoreCall(addTrunc(char, Type.BuiltIn.Number.Integer(8), "char"), result)
	addReturnCall(result)
}
/*private fun Module.addPrintlnFunction() = functionImpl(Functions.println) {
	val text = addLoadCall("text", arguments[0])
	val primitive = addPrimitive(primitive(Type.BuiltIn.Number.Integer(8).ptr()) {"\"%s\""}, "primitive")
	addFunctionCall(getFunctionByName("printf")!!, listOf(primitive, text), "ignored")
	addReturnCall(Null)
}
private fun Module.addScanlnFunction() {
}*/

private fun Module.addMallocImport() =
	addFunctionDeclaration("malloc", Type.BuiltIn.Number.Integer(8).ptr()).also { it.addArgument("size", Type.BuiltIn.Number.Integer(64)) }
private fun Module.addNativeFreeDef() =
	addFunctionDeclaration("free", Type.BuiltIn.VoidType).also { it.addArgument("what", Type.BuiltIn.Number.Integer(8).ptr()) }
private fun Module.addMangledFreeDef() =
	functionImpl(Functions.free) {
		addNotSavedFunctionCall(getFunctionByName("free")!!, listOf(addLoadCall(arguments[0], "ptr")))
		addReturnCall(IRElement.Named.Null)
	}
private fun Module.addLongAdditionFunction() =
	operation(Functions.addLong, LlvmFunction.FunctionImplementation::addAddCall)
private fun Module.addByteAdditionFunction() =
	operation(Functions.addByte, LlvmFunction.FunctionImplementation::addAddCall)
private fun Module.addLongSubtractionFunction() =
	operation(Functions.subLong, LlvmFunction.FunctionImplementation::addSubCall)
private fun Module.addByteSubtractionFunction() =
	operation(Functions.subByte, LlvmFunction.FunctionImplementation::addSubCall)
private fun Module.addLongMultiplication() =
	operation(Functions.mulLong, LlvmFunction.FunctionImplementation::addMulCall)
private fun Module.addByteMultiplication() =
	operation(Functions.mulByte, LlvmFunction.FunctionImplementation::addMulCall)
private fun Module.addLongDivision() =
	operation(Functions.divLong, LlvmFunction.FunctionImplementation::addSDivCall)
private fun Module.addByteDivision() =
	operation(Functions.divByte, LlvmFunction.FunctionImplementation::addSDivCall)
private fun Module.addLongRemainder() =
	operation(Functions.remLong, LlvmFunction.FunctionImplementation::addSRemCall)
private fun Module.addByteRemainder() =
	operation(Functions.remByte, LlvmFunction.FunctionImplementation::addSRemCall)
private fun Module.addLongShiftLeft() =
	operation(Functions.shlLong, LlvmFunction.FunctionImplementation::addShlCall)
private fun Module.addByteShiftLeft() =
	operation(Functions.shlByte, LlvmFunction.FunctionImplementation::addShlCall)
private fun Module.addLongShiftRight() =
	operation(Functions.shrLong, LlvmFunction.FunctionImplementation::addLShrCall)
private fun Module.addByteShiftRight() =
	operation(Functions.shrByte, LlvmFunction.FunctionImplementation::addLShrCall)
private fun Module.addLongAnd() =
	operation(Functions.andLong, LlvmFunction.FunctionImplementation::addAndCall)
private fun Module.addByteAnd() =
	operation(Functions.andByte, LlvmFunction.FunctionImplementation::addAndCall)
private fun Module.addLongOr() =
	operation(Functions.orLong, LlvmFunction.FunctionImplementation::addOrCall)
private fun Module.addByteOr() =
	operation(Functions.orByte, LlvmFunction.FunctionImplementation::addOrCall)
private fun Module.addLongXOr() =
	operation(Functions.xorLong, LlvmFunction.FunctionImplementation::addXOrCall)
private fun Module.addByteXOr() =
	operation(Functions.xorByte, LlvmFunction.FunctionImplementation::addXOrCall)
private fun Module.addByteAt() = functionImpl(Functions.byteAt) {
	addReturnCall(addLoadCall(arguments[0], "result", returnType))
}
private fun Module.addLongAt() = functionImpl(Functions.longAt) {
	addReturnCall(addLoadCall(addBitCast(arguments[0], type(Types.Long).ptr(), "ptr"), "result", returnType))
}
//private fun Module.addStringAt() = dataAt(Functions.stringAt)
private fun Module.addPointerAt() = functionImpl(Functions.pointerAt) {
	addReturnCall(addLoadCall(addBitCast(arguments[0], type(Types.Pointer).ptr(), "ptr"), "result", returnType))
}
private fun Module.addPointerOfByte() = pointerOf(Functions.pointerOfByte)
private fun Module.addPointerOfLong() = pointerOf(Functions.pointerOfLong)
//private fun Module.addPointerOfString() = pointerOf(Functions.pointerOfString)
private fun Module.addPointerOfPointer() = pointerOf(Functions.pointerOfPointer)
private fun Module.pointerOf(function: Function) = functionImpl(function) {
	addReturnCall(
		addStoreCall(addBitCast(arguments[0], type(Types.Pointer).child, "value"),
		addBitCast(addFunctionCall(getFunctionByName("malloc")!!, listOf(addPrimitive(primitive(Type.BuiltIn.Number.Integer(64)) { "4" })), "result"), type(Types.Pointer), "castedResult")
	))
}
private fun Module.addEqualByte() = functionImpl(Functions.equalByte) {
	addReturnCall(addFunctionCall(function(Functions.subByte), arguments, "result"))
}
/*private fun Module.addEqualLong(): Unit = functionImpl(Functions.equalLong) {
	val tmp = addFunctionCall(function(Functions.subLong), arguments, "result")
}*/

typealias OperationFunction = LlvmFunction.FunctionImplementation.(LlvmVariable, LlvmVariable, kotlin.String) -> LlvmVariable

private fun Module.operation(function: Function, operation: OperationFunction) = functionImpl(function) {
	val arg0 = addLoadCall(arguments[0], "argv0")
	val arg1 = addLoadCall(arguments[1], "argv1")
	val result = operation(arg0, arg1, "result")
	addReturnCall(addStoreCall(result, addBitCast(addFunctionCall(getFunctionByName("malloc")!!, listOf(addPrimitive(primitive(Type.BuiltIn.Number.Integer(64)) { size(function.returnType).toString() })), "resultPtr"), type(function.returnType), "castedResultPtr")))
}

private fun Module.functionImpl(function: Function, block: LlvmFunction.FunctionImplementation.() -> Unit) =
	addFunction(function.id(), type(function.returnType)).apply {
		function.arguments.forEach { addArgument(it.name, type(it.returnType)) }
		block()
	}

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
		function.addStoreCall(function.addLoadCall(addExpression(assignmentStatement.expression, tmpName()), tmpName()), variable(assignmentStatement.variableCall))

	private fun addMallocCall(type: dev.einsjannis.lang.compiler.Type, name: String) =
		function.addBitCast(function.addFunctionCall(module.getFunctionByName("malloc")!!, listOf(function.addPrimitive(primitive(Type.BuiltIn.Number.Integer(64)) { "${module.size(type)}" })), "\$malloc$name"), module.type(type), name)

	private fun addVariableDef(variableDef: VariableDef) =
		addMallocCall(variableDef.returnType, variableDef.name)

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
		val primitiveValue = function.addPrimitive(primitive.toValue())
		val variable = addMallocCall(primitive.returnType, varName ?: tmpName())
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
			//is String -> primitiveValue("\"${this.value}\"")
		}
	}

	@Suppress("IMPLICIT_CAST_TO_ANY")
	private fun variable(variableCall: VariableCall): LlvmVariable =
		(((function.code.findLast { it is LlvmVariable && it.name == variableCall.variable.name }
			as? LlvmVariable) ?: function.arguments.findLast { it.name == variableCall.variable.name }) ?: throw Exception("huh?"))

	private var tempCount: Int = 0

	private fun tmpName() = "\$tmp$tempCount".also { tempCount++ }

}
