package dev.einsjannis.lang.compiler

import kotlin.String
import kotlin.Boolean
import kotlin.Long
import kotlin.Byte

interface Named {
	val name: String
	fun id(): String = name
}

interface Type : Named

sealed interface Returnable {
	val returnType: Type
}

interface Variable : Named, Returnable

interface Function : Named, Returnable {
	val arguments: List<Variable>
	override fun id() = "$name${arguments.joinToString("") { "$${it.returnType.name}" }}"
}

interface FunctionImplementation : Function {
	val code: List<Statement>
}

sealed interface Statement

interface ConditionStatement : Statement {
	val condition: Expression
	val code: List<Statement>
	val other: ConditionStatement?
}

interface AssignmentStatement : Statement {
	val variableCall: VariableCall
	val expression: Expression
}

interface VariableDef : Statement, Variable

sealed interface Expression : Statement, Returnable

interface FunctionCall : Expression {
	val function: Function
	val arguments: List<Expression>
	override val returnType: Type
		get() = function.returnType
}

interface VariableCall : Expression {
	val variable: Variable
	override val returnType: Type
		get() = variable.returnType
}

sealed interface Primitive : Expression {
	val value: Any
}

sealed interface Number : Primitive {
	override val value: kotlin.Number
}

interface Long : Number {
	override val returnType: Type
		get() = Types.Long
	override val value: Long
}

interface Byte : Number {
	override val returnType: Type
		get() = Types.Byte
	override val value: Byte
}

interface Character : Primitive {
	override val returnType: Type
		get() = Types.Byte
	override val value: Char
}

/*interface String : Primitive {
	override val returnType: Type
		get() = Types.String
	override val value: String
}*/

interface Boolean : Primitive {
	override val returnType: Type
		get() = Types.Byte
	override val value: Boolean
}

interface ReturnStatement : Statement {
	val expression: Expression
}
