package dev.einsjannis.lang.scompiler

typealias Code = List<Statement>

sealed interface Definition {
	val name: kotlin.String
}

sealed interface FunctionDef : Definition {
	val templateArgumentDefs: List<TemplateArgumentDef>
	val returnType: ReturnType
	val argumentDefs: List<VariableDef>
}

interface VariableDef {
	val name: kotlin.String
	val type: ReturnType
}

interface VariableImpl : VariableDef {
	val initializer: Expression
}

interface ConstDef : VariableDef {
	val value: Const
}

sealed interface Const : TemplateArgument {
	val kotlinValue: Any
}

interface ReturnType : TemplateArgument {
	val templateArguments: List<TemplateArgument>
	val typeDef: TypeDef
}

sealed interface TemplateArgument

sealed interface TemplateArgumentDef {

	val name: kotlin.String

	interface Type : TemplateArgumentDef

	interface Const : TemplateArgumentDef, VariableDef

}

sealed interface TypeDef : Definition {
	val templateArgumentDefs: List<TemplateArgumentDef>
}

interface FunctionImplDef : FunctionDef {
	val code: Code
}

sealed interface Statement

sealed interface Expression : Statement {
	val returnType: ReturnType
}

interface VariableCall : Expression {
	val parent: VariableCall?
	val varDef: VariableDef
}

interface FunctionCall : Expression {
	val templateArguments: List<TemplateArgument>
	val arguments: List<Expression>
	val funDef: FunctionDef
}

sealed interface Primitive : Expression, Const

sealed interface Number : Primitive {
	override val kotlinValue: kotlin.Number
}

interface Integer : Number

interface Float : Number

interface Boolean : Primitive {
	override val kotlinValue: kotlin.Boolean
}

interface Character : Primitive {
	override val kotlinValue: kotlin.Char
}

interface String : Primitive {
	override val kotlinValue: kotlin.String
}

interface Conditional : Statement {
	val condition: Expression
	val code: Code
	val other: Conditional?
}

interface Assignment : Statement {
	val varCall: VariableCall
	val initializer: Expression
}
