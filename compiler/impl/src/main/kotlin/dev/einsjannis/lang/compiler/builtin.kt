package dev.einsjannis.lang.compiler

object Types {
	object Number : Type {
		override val name: kotlin.String = "Number"
	}
	val all = listOf(Number)
}

object Functions {
	object getPointerOfData : Function {
		override val name: kotlin.String = "getPointerOfData"
		override val arguments: List<Variable> = listOf(variable("number", Types.Number))
		override val returnType: Type = Types.Number
	}
	object getDataAtPointer : Function {
		override val name: kotlin.String = "getDataAtPointer"
		override val arguments: List<Variable> = listOf(variable("pointer", Types.Number))
		override val returnType: Type = Types.Number
	}
	object println : Function {
		override val name: kotlin.String = "println"
		override val arguments: List<Variable> = listOf(variable("string", Types.Number))
		override val returnType: Type = Types.Number
	}
	val all = listOf(getPointerOfData, getDataAtPointer, println)
}

internal fun variable(name: kotlin.String, type: Type): Variable = object : Variable {
	override val name: kotlin.String = name
	override val returnType: Type = type
}
