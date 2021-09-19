package dev.einsjannis.lang.compiler

import dev.einsjannis.AdvancedIterator
import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.compiler.parser.*
import dev.einsjannis.compiler.parser.Pattern
import dev.einsjannis.tupleOf

object Patterns {
	val Identifier: Pattern<kotlin.String> = sequence1(tupleOf(Tokens.Identifier.pattern)) { (token) -> token.content }
	val ReturnType: Pattern<Type> = sequence2(tupleOf(Tokens.Symbol.Colon.pattern, Identifier)) { (_, id) -> object : Type {
		override val name: kotlin.String = id
	} }
	val Number: Pattern<Number> = sequence1(tupleOf(Tokens.Primitive.Number.pattern)) { (token) ->
		val content = token.content
		val byte = content.endsWith("b")
		val numberStr = if (byte) content.substring(0, content.lastIndex) else content
		if (byte) {
			object : Byte {
				override val value: kotlin.Byte = numberStr.toByte()
			}
		} else {
			object : Long {
				override val value: kotlin.Long = numberStr.toLong()
			}
		}
	}
	val Character: Pattern<Character> = sequence1(tupleOf(Tokens.Primitive.Char.pattern)) { (token) -> object : Character {
		override val value: Char = token.content[1]
	} }
	val String: Pattern<String> = sequence1(tupleOf(Tokens.Primitive.String.pattern)) { (token) -> object : String {
		override val value: kotlin.String = token.content.substring(1, token.content.lastIndex)
	} }
	val Boolean: Pattern<Boolean> = sequence1(tupleOf(Tokens.Primitive.Boolean.pattern)) { (token) -> object : Boolean {
		override val value: kotlin.Boolean = token.content == "true"
	} }
	val Primitives: Pattern<Primitive> = superPattern(Number, Character, String, Boolean)
	val VariableCall: Pattern<VariableCall> = sequence1(tupleOf(Identifier)) { (id) -> VariableCallImpl(id) }
	val FunctionCall: Pattern<FunctionCall> = sequence2(tupleOf(Identifier, scopePattern(
		elementPattern = lazyPatternMap {
			Expression
		},
		separatorPattern = Tokens.Symbol.Comma.pattern,
		limiterPatterns = Tokens.Symbol.ParenthesesPattern
	))) { (id, args) -> FunctionCallImpl(id, args) }
	val Expression: Pattern<Expression> = superPattern(FunctionCall, VariableCall, Primitives)
	val Variable: Pattern<VariableDef> = sequence3(tupleOf(Tokens.Keyword.Var.pattern, Identifier, ReturnType)) { (_, id, type) -> object : VariableDef {
		override val name: kotlin.String = id
		override val returnType: Type = type
	} }
	val Assignment: Pattern<AssignmentStatement> = sequence3(tupleOf(
		VariableCall,
		Tokens.Symbol.EqualSign.pattern,
		Expression
	)) { (variableCall, _, expression) -> object : AssignmentStatement {
		override val variableCall: VariableCall = variableCall
		override val expression: Expression = expression
	} }
	val LazyCondition: Pattern<ConditionStatement> = lazyPatternMap { Condition }
	val Condition: Pattern<ConditionStatement> = sequence6(tupleOf(
		Tokens.Keyword.If.pattern,
		Tokens.Symbol.ParenthesesPattern.component1(),
		Expression,
		Tokens.Symbol.ParenthesesPattern.component2(),
		lazyPatternMap { Code },
		optional(sequence2(tupleOf(Tokens.Keyword.Else.pattern, LazyCondition)) { (_, condition) -> condition } )
	)) { (_, _, condition, _, code, other) -> object : ConditionStatement {
		override val condition: Expression = condition
		override val code: List<Statement> = code
		override val other: ConditionStatement? = other
	} }
	val Return: Pattern<ReturnStatement> = sequence2(tupleOf(Tokens.Keyword.Return.pattern, Expression)) { (_, expression) -> object : ReturnStatement {
		override val expression: Expression = expression
	} }
	val Code: Pattern<List<Statement>> = scopePattern(
		elementPattern = superPattern(Condition, Variable, Assignment, Return, Expression),
		separatorPattern = Tokens.Symbol.SemiColon.pattern,
		limiterPatterns = Tokens.Symbol.BracesPattern,
		requireTrailing = true
	)
	val ArgumentDefs: Pattern<List<Variable>> = scopePattern(
		elementPattern = sequence2(tupleOf(Identifier, ReturnType)) { (id, type) -> object : Variable {
			override val name: kotlin.String = id
			override val returnType: Type = type
		} },
		separatorPattern = Tokens.Symbol.Comma.pattern,
		limiterPatterns = Tokens.Symbol.ParenthesesPattern
	)
	val FunctionDefinitions: Pattern<List<FunctionImplementation>> = superScopePattern(
		sequence5(tupleOf(
			Tokens.Keyword.Fun.pattern, Identifier, ArgumentDefs, ReturnType, Code
		)) { (_, id, args, returnType, code) -> object : FunctionImplementation {
			override val name: kotlin.String = id
			override val arguments: List<Variable> = args
			override val returnType: Type = returnType
			override val code: List<Statement> = code
		} }
	)
	val Start: Pattern<List<FunctionImplementation>> = superPattern(FunctionDefinitions)
}

internal data class FunctionCallImpl(
	val functionName: kotlin.String,
	override val arguments: List<Expression>
) : FunctionCall {
	private var _function: Function? = null
	override var function: Function
		internal set(value) { _function = value }
		get() = _function ?: throw RuntimeException()
	fun id() = "$functionName${arguments.joinToString("") { "$${it.returnType.name}" }}"
}

internal data class VariableCallImpl(
	val variableName: kotlin.String,
) : VariableCall {
	private var _variable: Variable? = null
	override var variable: Variable
		internal set(value) { _variable = value }
		get() = _variable ?: throw RuntimeException()
}

fun parse(tokens: List<Token>): List<FunctionImplementation> = FileParser(Patterns.Start).parse(tokens)
