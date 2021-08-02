package dev.einsjannis.lang.compiler

sealed interface Declaration {
	val name: kotlin.String
}

sealed interface Function : Declaration {
	val arguments: List<Variable>
	val returnType: Type
}

fun Declaration.id() = buildString {
	append(name)
	if (this@id is Function) arguments.forEach { append("$${it.returnType.name}") }
}

fun Declaration.equalsExactly(other: Any?): kotlin.Boolean = other is Declaration && other.id() == this.id()

interface FunctionImplementation : Function {
	val code: List<Statement>
}

interface Type : Declaration

interface Variable : Statement, Declaration {
	val returnType: Type
}

sealed interface Statement

sealed interface Expression : Statement {
	val returnType: Type
}

interface Assignment : Statement {
	val variable: Variable
	val expression: Expression
}

interface VariableCall : Expression {
	val variable: Variable
	override val returnType: Type
		get() = variable.returnType
}

interface FunctionCall : Expression {
	val function: Function
	val arguments: List<Expression>
	override val returnType: Type
		get() = function.returnType
}

sealed interface Primitive : Expression {
	val value: Any
}

interface Number : Primitive {
	override val value: Long
	override val returnType: Type
		get() = Types.Number
}

interface Boolean : Primitive {
	override val value: kotlin.Boolean
	override val returnType: Type
		get() = Types.Number
}

interface String : Primitive {
	override val value: kotlin.String
	override val returnType: Type
		get() = Types.Number
}

interface Char : Primitive {
	override val value: kotlin.Char
	override val returnType: Type
		get() = Types.Number
}

interface ReturnStatement : Statement {
	val expression: Expression
}
