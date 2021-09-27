package dev.einsjannis.lang.compiler

object Functions {
	val putChar = function("putChar", Types.Unit, variable("char", Types.Byte))
	val getChar = function("getChar", Types.Byte)
	val free = function("free", Types.Unit, variable("tofree", Types.Pointer))
	private fun operation(type: Type, name: String) =
		function(name, type, variable("arg0", type), variable("arg1", type))
	val addLong = operation(Types.Long, "add")
	val addByte = operation(Types.Byte, "add")
	val subLong = operation(Types.Long, "sub")
	val subByte = operation(Types.Byte, "sub")
	val mulLong = operation(Types.Long, "mul")
	val mulByte = operation(Types.Byte, "mul")
	val divLong = operation(Types.Long, "div")
	val divByte = operation(Types.Byte, "div")
	val remLong = operation(Types.Long, "rem")
	val remByte = operation(Types.Byte, "rem")
	val shlLong = operation(Types.Long, "shl")
	val shlByte = operation(Types.Byte, "shl")
	val shrLong = operation(Types.Long, "shr")
	val shrByte = operation(Types.Byte, "shr")
	val andLong = operation(Types.Long, "and")
	val andByte = operation(Types.Byte, "and")
	val orLong = operation(Types.Long, "or")
	val orByte = operation(Types.Byte, "or")
	val xorLong = operation(Types.Long, "xor")
	val xorByte = operation(Types.Byte, "xor")
	val byteAt = function("byteAt", Types.Byte, variable("pointer", Types.Pointer))
	val longAt = function("longAt", Types.Long, variable("pointer", Types.Pointer))
	val pointerAt = function("pointerAt", Types.Pointer, variable("pointer", Types.Pointer))
	val pointerOfByte = function("pointerOf", Types.Pointer, variable("byte", Types.Byte))
	val pointerOfLong = function("pointerOf", Types.Pointer, variable("long", Types.Long))
	val pointerOfPointer = function("pointerOf", Types.Pointer, variable("pointer", Types.Pointer))
	val equalByte = operation(Types.Byte, "equals")
	val equalLong =
		function("equals", Types.Byte, variable("arg0", Types.Long), variable("arg1", Types.Long))
	val notByte =
		function("not", Types.Byte, variable("arg0", Types.Byte), variable("arg1", Types.Byte))
	val notLong =
		function("not", Types.Byte, variable("arg0", Types.Long), variable("arg1", Types.Long))
	val pointerToLong = function("pointerToLong", Types.Long, variable("pointer", Types.Pointer))
	val longToPointer = function("longToPointer", Types.Pointer, variable("long", Types.Long))
	val all: List<Function> = listOf(putChar, getChar, free, addLong, addByte, subLong, subByte, mulLong, mulByte,
		divLong, divByte, remLong, remByte, shlLong, shlByte, shrLong, shrByte, andLong, andByte, orLong, orByte,
		xorLong, xorByte, byteAt, longAt, pointerAt, pointerOfByte, pointerOfLong, pointerOfPointer, equalByte,
		equalLong, notByte, notLong, pointerToLong, longToPointer)
}

object Types {
	val Pointer = type("Pointer")
	val Long = type("Long")
	val Byte = type("Byte")
	val Unit = type("Unit")
	val all: List<Type> = listOf(Pointer, Long, Byte, Unit)
}

fun function(name: String, returnType: Type, vararg arguments: Variable) = object : Function {
	override val name: String = name
	override val arguments: List<Variable> = arguments.toList()
	override val returnType: Type = returnType
}

fun type(name: String) = object : Type {
	override val name: String = name
}

fun variable(name: String, returnType: Type) = object : Variable {
	override val name: String = name
	override val returnType: Type = returnType
}
