package dev.einsjannis.lang.scompiler

import dev.einsjannis.AdvancedIterator
import dev.einsjannis.compiler.parser.*
import dev.einsjannis.compiler.parser.Pattern
import dev.einsjannis.tupleOf

import dev.einsjannis.lang.scompiler.Definition as IRDefinition
import dev.einsjannis.lang.scompiler.FunctionImplDef as IRFunctionImplDef
import dev.einsjannis.lang.scompiler.TemplateArgumentDef as IRTemplateArgumentDef
import dev.einsjannis.lang.scompiler.ReturnType as IRReturnType
import dev.einsjannis.lang.scompiler.Code as IRCode
import dev.einsjannis.lang.scompiler.VariableDef as IRVariableDef
import dev.einsjannis.lang.scompiler.TemplateArgument as IRTemplateArgument
import dev.einsjannis.lang.scompiler.Statement as IRStatement
import dev.einsjannis.lang.scompiler.Expression as IRExpression
import dev.einsjannis.lang.scompiler.FunctionCall as IRFunctionCall
import dev.einsjannis.lang.scompiler.VariableCall as IRVariableCall
import dev.einsjannis.lang.scompiler.Primitive as IRPrimitive
import dev.einsjannis.lang.scompiler.Number as IRNumber
import dev.einsjannis.lang.scompiler.Integer as IRInteger
import dev.einsjannis.lang.scompiler.Float as IRFloat
import dev.einsjannis.lang.scompiler.Character as IRCharacter
import dev.einsjannis.lang.scompiler.String as IRString
import dev.einsjannis.lang.scompiler.Assignment as IRAssignment
import dev.einsjannis.lang.scompiler.Conditional as IRConditional
import dev.einsjannis.lang.scompiler.Boolean as IRBoolean

object Pattern {

	object Definitions : Pattern<List<IRDefinition>> {

		override fun match(tokens: AdvancedIterator<dev.einsjannis.compiler.lexer.Token>): Match<List<IRDefinition>> {
			tokens.pushContext()
			val list = buildList {
				while (true) {
					val elementMatch = tokens.match(Definition)
					if (elementMatch is NoMatch) {
						tokens.popContext()
						return NoMatch(NoMatch.Cause.PatternMissMatch(Definition, elementMatch.cause))
					}
					add(elementMatch.node)
					if (!tokens.hasNext()) break
				}
			}
			tokens.clearContext()
			return ValidMatch(list)
		}

	}

	object Definition : Pattern<IRDefinition> by superPattern(FunctionImplDef)

	object FunctionImplDef : Pattern<IRFunctionImplDef> by sequence6(
		tupleOf(Token.Keyword.Function.pattern, TemplateArgumentDefs, Token.Identifier.pattern, ArgumentDefs, ReturnType, Code),
		{ (_, templateArgDef, identifier, argumentDef, returnType, code) -> object : IRFunctionImplDef {
			override val templateArgumentDefs = templateArgDef
			override val name = identifier.content
			override val argumentDefs = argumentDef
			override val returnType = returnType
			override val code = code
		} }
	)

	object TemplateArgumentDefs : Pattern<List<IRTemplateArgumentDef>> by scopePattern(
		elementPattern = TemplateArgumentDef,
		separatorPattern = Token.Symbol.Comma.pattern,
		limiterPatterns = tupleOf(Token.Symbol.LessThen.pattern, Token.Symbol.GreaterThen.pattern),
	)

	object TemplateArgumentDef : Pattern<IRTemplateArgumentDef> by superPattern(TypeArgDef, ConstArgDef)

	object TypeArgDef : Pattern<IRTemplateArgumentDef.Type> by sequence1(
		tupleOf(Token.Identifier.pattern),
		{ (name) -> object : IRTemplateArgumentDef.Type {
			override val name = name.content
		} }
	)

	object ConstArgDef : Pattern<IRTemplateArgumentDef.Const> by sequence3(
		tupleOf(Token.Keyword.Constant.pattern, Token.Identifier.pattern, ReturnType),
		{ (_, name, returnType) -> object : IRTemplateArgumentDef.Const {
			override val name = name.content
			override val type = returnType
		} }
	)

	object ArgumentDefs : Pattern<List<IRVariableDef>> by scopePattern(
		ArgumentDef,
		Token.Symbol.Comma.pattern,
		tupleOf(Token.Symbol.ParenthesesL.pattern, Token.Symbol.ParenthesesR.pattern)
	)

	object ArgumentDef : Pattern<IRVariableDef> by sequence2(
		tupleOf(Token.Identifier.pattern, ReturnType),
		{ (name, returnType) -> VariableDefImpl(name.content, returnType) }
	)

	object ReturnType : Pattern<IRReturnType> by sequence3(
		tupleOf(Token.Symbol.Colon.pattern, Token.Identifier.pattern, TemplateArguments),
		{ (_, name, templateArgs) -> ReturnTypeImpl(name.content, templateArgs) }
	)

	object TemplateArguments : Pattern<List<IRTemplateArgument>> by scopePattern(
		TemplateArgument,
		Token.Symbol.Comma.pattern,
		tupleOf(Token.Symbol.LessThen.pattern, Token.Symbol.GreaterThen.pattern)
	)

	object TemplateArgument : Pattern<IRTemplateArgument> by superPattern()

	object Code : Pattern<IRCode> by scopePattern(
		Statement,
		Token.Symbol.SemiColon.pattern,
		tupleOf(Token.Symbol.BracesL.pattern, Token.Symbol.BracesR.pattern)
	)

	object Statement : Pattern<IRStatement> by superPattern(Expression, Assignment, Conditional, VariableDef)

	object Expression : Pattern<IRExpression> by superPattern(FunctionCall, VariableCall, Primitive)

	object FunctionCall : Pattern<IRFunctionCall> by sequence3(
		tupleOf(Token.Identifier.pattern, optional(TemplateArguments), Arguments),
		{ (name, templateArgs, args) -> FunctionCallImpl(name.content, templateArgs ?: listOf(), args) }
	)

	object Arguments : Pattern<List<IRExpression>> by scopePattern(
		Expression,
		Token.Symbol.Comma.pattern,
		tupleOf(Token.Symbol.ParenthesesL.pattern, Token.Symbol.ParenthesesR.pattern)
	)

	object VariableCall : Pattern<IRVariableCall> by sequence2(
		tupleOf(Token.Identifier.pattern, optional(sequence2(
			tupleOf(Token.Symbol.Dot.pattern, VariableCall),
			{ (_, call) -> call }
		))),
		{ (name, child) ->
			if (child != null) {
				(child as VariableCallImpl).parent = VariableCallImpl(name.content)
				child
			} else {
				VariableCallImpl(name.content)
			}
		}
	)

	object Primitive : Pattern<IRPrimitive> by superPattern(Number, Boolean, Char, String)

	object Number : Pattern<IRNumber> by superPattern(Integer, Float)

	object Integer : Pattern<IRInteger> by sequence1(
		tupleOf(Token.Primitive.Number.Integer.pattern),
		{ (token) -> object : IRInteger {
			override val kotlinValue: kotlin.Number = token.content.toInt()
			override val returnType: IRReturnType = object : IRReturnType {
				override val templateArguments = emptyList<Nothing>()
				override val typeDef: TypeDef = Type.Integer
			}
		} }
	)

	object Float : Pattern<IRFloat> by sequence1(
		tupleOf(Token.Primitive.Number.Float.pattern),
		{ (token) -> object : IRFloat {
			override val kotlinValue: kotlin.Number = token.content.toFloat()
			override val returnType: IRReturnType = object : IRReturnType {
				override val templateArguments = emptyList<Nothing>()
				override val typeDef: TypeDef = Type.Float
			}
		} }
	)

	object Boolean : Pattern<IRBoolean> by sequence1(
		tupleOf(Token.Primitive.Boolean.pattern),
		{ (token) -> object : IRBoolean {
			override val kotlinValue: kotlin.Boolean = token.content.toBooleanStrict()
			override val returnType: IRReturnType = object : IRReturnType {
				override val templateArguments: List<IRTemplateArgument> = emptyList()
				override val typeDef: TypeDef = Type.Boolean
			}
		} }
	)

	object Char : Pattern<IRCharacter> by sequence1(
		tupleOf(Token.Primitive.Char.pattern),
		{ (token) -> object : IRCharacter {
			override val kotlinValue: kotlin.Char = token.content[1]
			override val returnType: IRReturnType = object : IRReturnType {
				override val templateArguments: List<IRTemplateArgument> = emptyList()
				override val typeDef: TypeDef = Type.Character
			}
		} }
	)

	object String : Pattern<IRString> by sequence1(
		tupleOf(Token.Primitive.String.pattern),
		{ (token) -> object : IRString {
			override val kotlinValue: kotlin.String = token.content.substring(1, token.content.lastIndex)
			override val returnType: IRReturnType = object : IRReturnType {
				override val templateArguments: List<IRTemplateArgument> = emptyList()
				override val typeDef: TypeDef = Type.String
			}
		} }
	)

	object Assignment : Pattern<IRAssignment> by sequence3(
		tupleOf(VariableCall, Token.Symbol.EqualSign.pattern, Expression),
		{ (call, _, initializer) -> object : IRAssignment {
			override val varCall = call
			override val initializer = initializer
		} }
	)

	object Conditional : Pattern<IRConditional> by sequence6(
		tupleOf(Token.Keyword.If.pattern, Token.Symbol.ParenthesesL.pattern, Expression, Token.Symbol.ParenthesesR.pattern, Code, OtherBranch),
		{ (_, _, condition, _, code, otherBranch) -> object : IRConditional {
			override val code: IRCode = code
			override val condition: IRExpression = condition
			override val other: IRConditional? = otherBranch
		} }
	)

	object OtherBranch : Pattern<IRConditional?> by optional(superPattern(
		sequence7(
			tupleOf(Token.Keyword.Else.pattern, Token.Keyword.If.pattern, Token.Symbol.ParenthesesL.pattern, Expression, Token.Symbol.ParenthesesR.pattern, Code, OtherBranch),
			{ (_, _, _, condition, _, code, otherBranch) -> object : IRConditional {
				override val code: IRCode = code
				override val condition: IRExpression = condition
				override val other: IRConditional? = otherBranch
			} }
		),
		sequence2(
			tupleOf(Token.Keyword.Else.pattern, Code),
			{ (_, code) -> object : IRConditional {
				override val code: IRCode = code
				override val condition: IRExpression = object : IRBoolean {
					override val kotlinValue: kotlin.Boolean = true
					override val returnType: IRReturnType = object : IRReturnType {
						override val templateArguments: List<IRTemplateArgument> = emptyList()
						override val typeDef: TypeDef = Type.Boolean
					}
				}
				override val other: IRConditional? = null
			} }
		)
	))

	object VariableDef : Pattern<IRVariableDef> by sequence4(
		tupleOf(Token.Keyword.Variable.pattern, Token.Identifier.pattern, ReturnType, optional(sequence2(
			tupleOf(Token.Symbol.EqualSign.pattern, Expression),
			{ (_, initializer) -> initializer }
		))),
		{ (_, name, returnType, initializer) ->
			if (initializer != null)
				VariableImplImpl(name.content, returnType, initializer)
			else
				VariableDefImpl(name.content, returnType)
		}
	)

}

internal open class VariableDefImpl(
	override val name: String,
	override val type: IRReturnType
) : IRVariableDef

internal class VariableImplImpl(
	name: String,
	type: IRReturnType,
	override val initializer: IRExpression
) : VariableDefImpl(name, type), VariableImpl

data class ReturnTypeImpl(val name: kotlin.String, override val templateArguments: List<IRTemplateArgument>) : IRReturnType {

	internal var _typeDef: TypeDef? = null

	override val typeDef: TypeDef get() = _typeDef ?: throw Exception("Not initialized yet")

}

data class FunctionCallImpl(
	val name: String,
	override val templateArguments: List<IRTemplateArgument>,
	override val arguments: List<IRExpression>
) : IRFunctionCall {

	internal var _funDef: FunctionDef? = null

	override val funDef: FunctionDef get() = _funDef ?: throw Exception("Not initialized yet")

	override val returnType: IRReturnType get() = funDef.returnType

}

data class VariableCallImpl(
	val name: String
) : IRVariableCall {

	override var parent: IRVariableCall? = null

	override val returnType: IRReturnType get() = varDef.type

	internal var _varDef: IRVariableDef? = null

	override val varDef: IRVariableDef get() = _varDef ?: throw Exception("Not initialized yet")

}
