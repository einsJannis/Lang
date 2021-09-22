package dev.einsjannis.lang.compiler

object Functions {
	//val println = function("println", Types.Unit, variable("text", Types.String))
	val putChar = function("putChar", Types.Unit, variable("char", Types.Byte))
	val getChar = function("getChar", Types.Byte)
	private fun operation(type: Type, name: kotlin.String) =
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
	//val stringAt = function("stringAt", Types.String, variable("pointer", Types.Pointer))
	val pointerAt = function("pointerAt", Types.Pointer, variable("pointer", Types.Pointer))
	val pointerOfByte = function("pointerOf", Types.Pointer, variable("byte", Types.Byte))
	val pointerOfLong = function("pointerOf", Types.Pointer, variable("long", Types.Long))
	//val pointerOfString = function("pointerOf", Types.Pointer, variable("string", Types.String))
	val pointerOfPointer = function("pointerOf", Types.Pointer, variable("pointer", Types.Pointer))
	val equalByte = operation(Types.Byte, "equals")
	val equalLong = function("equals", Types.Byte, variable("arg0", Types.Long), variable("arg1", Types.Long))
	//val equalString = function("equals", Types.Byte, variable("arg0", Types.String), variable("arg1", Types.String))
	val equalPointer = function("equals", Types.Byte, variable("arg0", Types.Pointer), variable("arg1", Types.Pointer))
	val notByte = operation(Types.Byte, "not")
	val notLong = operation(Types.Long, "not")
	val pointerToLong = function("pointerToLong", Types.Long, variable("pointer", Types.Pointer))
	val longToPointer = function("longToPointer", Types.Pointer, variable("long", Types.Long))
	val all: List<Function> = listOf(
		putChar,
		getChar,
		//println,
		addLong,
		addByte,
		subLong,
		subByte,
		mulLong,
		mulByte,
		divLong,
		divByte,
		remLong,
		remByte,
		shlLong,
		shlByte,
		shrLong,
		shrByte,
		andLong,
		andByte,
		orLong,
		orByte,
		xorLong,
		xorByte,
		byteAt,
		longAt,
		//stringAt,
		pointerAt,
		pointerOfByte,
		pointerOfLong,
		//pointerOfString,
		pointerOfPointer,
		equalByte,
		//equalLong,
		//equalString,
		//equalPointer,
		//notByte,
		//notLong,
		//pointerToLong,
		//longToPointer
	)
}

object Types {
	val Pointer = type("Pointer")
	//val String = type("String")
	val Long = type("Long")
	val Byte = type("Byte")
	val Unit = type("Unit")
	val all: List<Type> = listOf(Pointer, /*String,*/ Long, Byte, Unit)
}

fun function(name: kotlin.String, returnType: Type, vararg arguments: Variable) = object : Function {
	override val name: kotlin.String = name
	override val arguments: List<Variable> = arguments.toList()
	override val returnType: Type = returnType
}

fun type(name: kotlin.String) = object : Type {
	override val name: kotlin.String = name
}

fun variable(name: kotlin.String, returnType: Type) = object : Variable {
	override val name: kotlin.String = name
	override val returnType: Type = returnType
}
