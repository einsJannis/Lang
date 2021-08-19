package dev.einsjannis.lang.compiler

import dev.einsjannis.lang.compiler.FunctionGenerator.Companion.addFunction
import dev.einsjannis.compiler.llvm.Function as LlvmFunction
import dev.einsjannis.compiler.llvm.Module
import dev.einsjannis.compiler.llvm.PrimitiveValue
import dev.einsjannis.compiler.llvm.ptr
import dev.einsjannis.compiler.llvm.Variable as LlvmVariable
import dev.einsjannis.compiler.llvm.Type as LlvmType

fun generateLlvmIR(functions: List<FunctionImplementation>): kotlin.String {
	val module = Module.new()
	functions.forEach { module.addFunction(it) }
	return module.generateIR()
}

@Suppress("UNUSED")
fun Module.type(type: Type): LlvmType = when {
	type.id() == Types.Number.id() -> LlvmType.BuiltIn.Number.Integer(64).ptr()
	else -> throw IllegalStateException()
}

fun Module.function(function: FunctionImplementation): LlvmFunction =
	getFunctionByName(function.name) ?: throw IllegalStateException()

class FunctionGenerator(private val module: Module, private val function: LlvmFunction.FunctionImplementation) {

	@Suppress("IMPLICIT_CAST_TO_ANY")
	private fun variable(variableCall: VariableCall): LlvmVariable =
		(((function.code.find { it is LlvmVariable && it.name == variableCall.variable.name }
			as? LlvmVariable) ?: function.arguments.find { it.name == variableCall.variable.name }) ?: throw Exception("huh?"))

	private var tempCount: Int = 0

	private fun generateTempName() = "\$tmp$tempCount".also { tempCount++ }

	private fun addArgument(argument: Variable) {
		function.addArgument(argument.name, module.type(argument.returnType))
	}

	private fun addPrintlnCall(functionCall: FunctionCall): LlvmVariable {
		val arg = addExpression(functionCall.arguments.first())
		val string = function.addPrimitive(LlvmStringPrimitiveValue("%s"), generateTempName())
		return function.addFunctionCall(
			function = module.getFunctionByName("printf")!!,
			arguments = listOf(string, arg),
			returnName = generateTempName()
		)
	}

	private fun addFunctionCall(functionCall: FunctionCall, varName: kotlin.String? = null): LlvmVariable =
		when (functionCall.function) {
			Functions.getDataAtPointer -> TODO()
			Functions.getPointerOfData -> TODO()
			Functions.println -> addPrintlnCall(functionCall)
			is FunctionImplementation -> {
				val toCall = module.function(functionCall.function as FunctionImplementation)
				val arguments = functionCall.arguments.map { addExpression(it) }
				function.addFunctionCall(toCall, arguments, varName ?: generateTempName())
			}
		}

	private fun addVariableCall(variableCall: VariableCall, varName: kotlin.String? = null): LlvmVariable =
		function.addVarAlias(variable(variableCall), varName ?: generateTempName())

	private fun addPrimitive(primitive: Primitive, varName: kotlin.String? = null): LlvmVariable =
		function.addPrimitive(primitive.toPrimitiveValue(), varName ?: generateTempName())

	private fun addExpression(expression: Expression, varName: kotlin.String? = null) = when (expression) {
		is FunctionCall -> addFunctionCall(expression, varName)
		is VariableCall -> addVariableCall(expression, varName)
		is Primitive -> addPrimitive(expression, varName)
	}

	private fun addAssignment(assignment: Assignment) = addExpression(assignment.expression, assignment.variable.name)

	private fun addReturnStatement(returnStatement: ReturnStatement) =
		function.addReturnStatement(addExpression(returnStatement.expression))

	private fun addAllocationCall(variable: Variable) =
		function.addAllocationCall(module.type(variable.returnType), variable.name)

	@Suppress("IMPLICIT_CAST_TO_ANY")
	internal fun addStatement(statement: Statement) = when (statement) {
		is Assignment -> addAssignment(statement)
		is Expression -> addExpression(statement)
		is ReturnStatement -> addReturnStatement(statement)
		is Variable -> addAllocationCall(statement)
	}

	companion object {
		private var initializedModules: MutableList<Module> = mutableListOf()
		private var Module.initialized: kotlin.Boolean
			get() = initializedModules.contains(this)
			set(value) { if (value) initializedModules.add(this) else initializedModules.remove(this) }
		private fun Module.addPrintfImport() =
			addFunctionDeclaration("printf", LlvmType.BuiltIn.Number.Integer(32))
		private fun Module.initialize() {
			addPrintfImport()
			initialized = true
		}
		fun Module.addFunction(function: FunctionImplementation) {
			if (!this.initialized) this.initialize()
			val f = addFunction(function.name, type(function.returnType))
			val generator = FunctionGenerator(this, f)
			function.arguments.forEach { generator.addArgument(it) }
			function.code.forEach { generator.addStatement(it) }
		}
	}

	private fun Primitive.toPrimitiveValue(): PrimitiveValue = when (this) {
		is Boolean -> NumberPrimitiveValue(if (value) 1 else 0)
		is Char -> NumberPrimitiveValue(value.code.toLong())
		is Number -> NumberPrimitiveValue(value)
		is String -> NumberArrayPrimitiveValue(value.map { it.code.toLong() }.toLongArray())
	}

	inner class NumberPrimitiveValue(val value: Long) : PrimitiveValue() {
		override val type: LlvmType = module.type(Types.Number)
		override fun asString(): kotlin.String = TODO()
	}

	inner class NumberArrayPrimitiveValue(val value: LongArray) : PrimitiveValue() {
		override val type: dev.einsjannis.compiler.llvm.Type = module.type(Types.Number)
		override fun asString(): kotlin.String = TODO()
	}

	inner class LlvmStringPrimitiveValue(val string: kotlin.String) : PrimitiveValue() {
		override val type: LlvmType = LlvmType.BuiltIn.Array(LlvmType.BuiltIn.Number.Integer(8), string.length)
		override fun asString(): kotlin.String = "\"$string\""
	}

}
