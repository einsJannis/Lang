package dev.einsjannis.lang.compiler

import dev.einsjannis.compiler.llvm.*
import dev.einsjannis.compiler.llvm.Type
import dev.einsjannis.compiler.llvm.Function as LlvmFunction
import dev.einsjannis.compiler.llvm.Variable as LlvmVariable

@Suppress("UNUSED")
fun Module.type(type: dev.einsjannis.lang.compiler.Type): Type.BuiltIn.PointerType<Type> = when {
	type.id() == Types.Byte.id() -> Type.BuiltIn.Number.Integer(8).ptr()
	type.id() == Types.Long.id() -> Type.BuiltIn.Number.Integer(64).ptr()
	type.id() == Types.Pointer.id() -> Type.BuiltIn.Number.Integer(8).ptr().ptr()
	type.id() == Types.Unit.id() -> Type.BuiltIn.Number.Integer(8).ptr()
	else -> throw IllegalStateException()
}

@Suppress("UNUSED")
fun Module.size(type: dev.einsjannis.lang.compiler.Type): kotlin.Long = when {
	type.id() == Types.Byte.id() -> 1L
	type.id() == Types.Long.id() -> 4L
	type.id() == Types.Pointer.id() -> 4L
	type.id() == Types.Unit.id() -> 1L
	else -> throw IllegalStateException()
}

fun Module.initialize() {
	addMallocImport()
	addPutCharImport()
	addGetCharImport()
	addFreeImport()
	addMalloc()
	addPutChar()
	addGetChar()
	addFree()
	addUnit()
	operation(Functions.addByte, LlvmFunction.FunctionImplementation::addAddCall)
	operation(Functions.addLong, LlvmFunction.FunctionImplementation::addAddCall)
	operation(Functions.subByte, LlvmFunction.FunctionImplementation::addSubCall)
	operation(Functions.subLong, LlvmFunction.FunctionImplementation::addSubCall)
	operation(Functions.mulByte, LlvmFunction.FunctionImplementation::addMulCall)
	operation(Functions.mulLong, LlvmFunction.FunctionImplementation::addMulCall)
	operation(Functions.divByte, LlvmFunction.FunctionImplementation::addSDivCall)
	operation(Functions.divLong, LlvmFunction.FunctionImplementation::addSDivCall)
	operation(Functions.remByte, LlvmFunction.FunctionImplementation::addSRemCall)
	operation(Functions.remLong, LlvmFunction.FunctionImplementation::addSRemCall)
	operation(Functions.shlByte, LlvmFunction.FunctionImplementation::addShlCall)
	operation(Functions.shlLong, LlvmFunction.FunctionImplementation::addShlCall)
	operation(Functions.shrByte, LlvmFunction.FunctionImplementation::addLShrCall)
	operation(Functions.shrLong, LlvmFunction.FunctionImplementation::addLShrCall)
	operation(Functions.andByte, LlvmFunction.FunctionImplementation::addAndCall)
	operation(Functions.andLong, LlvmFunction.FunctionImplementation::addAndCall)
	operation(Functions.orByte, LlvmFunction.FunctionImplementation::addOrCall)
	operation(Functions.orLong, LlvmFunction.FunctionImplementation::addOrCall)
	operation(Functions.xorByte, LlvmFunction.FunctionImplementation::addXOrCall)
	operation(Functions.xorLong, LlvmFunction.FunctionImplementation::addXOrCall)
	addXAtFunction(Functions.byteAt)
	addXAtFunction(Functions.longAt)
	addXAtFunction(Functions.pointerAt)
	addPointerOfFunction(Functions.pointerOfByte)
	addPointerOfFunction(Functions.pointerOfLong)
	addPointerOfFunction(Functions.pointerOfPointer)
	addEqualsFunction(Functions.equalByte)
	addEqualsFunction(Functions.equalLong)
	addNotFunction(Functions.notByte)
	addNotFunction(Functions.notLong)
	addCastFunction(Functions.pointerToLong)
	addCastFunction(Functions.longToPointer)
}

private fun Module.addPutCharImport() =
	addFunctionImport("putchar", Type.BuiltIn.Number.Integer(16)) {
		addArgument("char", Type.BuiltIn.Number.Integer(16))
	}

private fun Module.addGetCharImport() =
	addFunctionImport("getchar", Type.BuiltIn.Number.Integer(16))

private fun Module.addFunctionImport(
	name: String,
	returnType: Type,
	argumentsBuilder: LlvmFunction.FunctionDeclaration.() -> Unit = {}
) = addFunctionDeclaration(name, returnType).apply(argumentsBuilder)

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

private fun Module.addMallocImport() =
	addFunctionImport("malloc", Type.BuiltIn.Number.Integer(8).ptr()) {
		addArgument("size", Type.BuiltIn.Number.Integer(64))
	}

private fun Module.addFreeImport() =
	addFunctionImport("free", Type.BuiltIn.VoidType) {
		addArgument("what", Type.BuiltIn.Number.Integer(8).ptr())
	}

private fun Module.addMalloc() = functionImpl(Functions.malloc) {
	addReturnCall(addStoreCall(addFunctionCall(
		getFunctionByName("malloc")!!,
		listOf(addLoadCall(arguments[0], "tmp0")),
		"tmp1"
	), addFunctionCall(
		getFunctionByName("malloc")!!,
		listOf(addPrimitive(primitive(Type.BuiltIn.Number.Integer(64)) { "1" })),
		"tmp2"
	)))
}

private fun Module.addFree() =
	functionImpl(Functions.free) {
		addNotSavedFunctionCall(getFunctionByName("free")!!, listOf(addLoadCall(arguments[0], "ptr")))
		addNotSavedFunctionCall(getFunctionByName("free")!!, listOf(arguments[0]))
		addReturnCall(IRElement.Named.Null)
	}

private fun Module.addUnit() = functionImpl(Functions.unit) {
	addReturnCall(IRElement.Named.Null)
}

private fun Module.addXAtFunction(function: Function) = functionImpl(function) {
	addReturnCall(addLoadCall(addBitCast(arguments[0], returnType.ptr(), "ptr"), "result"))
}

private fun Module.addPointerOfFunction(function: Function) = functionImpl(function) {
	addReturnCall(
		addStoreCall(addBitCast(arguments[0], type(Types.Pointer).child, "value"),
			addBitCast(addFunctionCall(
				getFunctionByName("malloc")!!,
				listOf(addPrimitive(primitive(Type.BuiltIn.Number.Integer(64)) { "4" })),
				"result"
			), type(Types.Pointer), "castedResult")
		))
}

private fun Module.addEqualsFunction(function: Function) = functionImpl(function) {
	addReturnCall(addStoreCall(addZext(
		addIcmpCall(
			Code.IcmpCall.Operator.EQ,
			addLoadCall(arguments[0], "tmp0"),
			addLoadCall(arguments[1], "tmp1"),
			"tmp2"
		),
		type(Types.Byte).child,
		"tmp3"
	), addFunctionCall(
		getFunctionByName("malloc")!!,
		listOf(addPrimitive(primitive(Type.BuiltIn.Number.Integer(64)) { "1" })),
		"tmp4"
	)))
}

private fun Module.addNotFunction(function: Function) = functionImpl(function) {
	addReturnCall(addStoreCall(
		addZext(
			addIcmpCall(
				Code.IcmpCall.Operator.NE,
				addLoadCall(arguments[0], "tmp0"),
				addPrimitive(primitive(arguments[0].type) { "0" }),
				"tmp1"
			),
			type(Types.Byte).child,
			"tmp2"
		),
		addFunctionCall(
			getFunctionByName("malloc")!!,
			listOf(addPrimitive(primitive(Type.BuiltIn.Number.Integer(64)) { "1" })),
			"tmp3"
		)
	))
}

private fun Module.addCastFunction(function: Function) = functionImpl(function) {
	addReturnCall(addBitCast(arguments[0], returnType, "result"))
}

typealias OperationFunction = LlvmFunction.FunctionImplementation.(LlvmVariable, LlvmVariable, String) -> LlvmVariable

private fun Module.operation(function: Function, operation: OperationFunction) =
	functionImpl(function) {
		addReturnCall(addStoreCall(
			operation(addLoadCall(arguments[0], "argv0"), addLoadCall(arguments[1], "argv1"), "result"),
			addBitCast(addFunctionCall(
				getFunctionByName("malloc")!!,
				listOf(addPrimitive(primitive(Type.BuiltIn.Number.Integer(64)) {
					size(function.returnType).toString()
				})
				), "resultPtr"),
				type(function.returnType),
				"castedResultPtr")
		))
	}

private fun Module.functionImpl(function: Function, block: LlvmFunction.FunctionImplementation.() -> Unit) =
	addFunction(function.id(), type(function.returnType)).apply {
		function.arguments.forEach { addArgument(it.name, type(it.returnType)) }
		block()
	}

