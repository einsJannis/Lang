package dev.einsjannis.lang.compiler

import dev.einsjannis.AdvancedIterator
import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.compiler.parser.*
import dev.einsjannis.tupleOf
import java.lang.IllegalStateException

object Patterns {

	val IdentifierPattern : Pattern<kotlin.String> = sequence1(tupleOf(Tokens.Identifier.pattern)) { it.component1().content }

	val ReturnTypePattern : Pattern<Type> =
		sequence2(tupleOf(Tokens.SymbolColon.pattern, IdentifierPattern)) { (_, name) -> object : Type {
			override val name: kotlin.String = name
		} }

	val ArgumentsPattern : Pattern<List<Expression>> = scopePattern(
		elementPattern = lazyPatternMap { ExpressionPattern },
		separatorPattern = Tokens.SymbolComma.pattern,
		limiterPatterns = tupleOf(Tokens.SymbolBracketsBracesL.pattern, Tokens.SymbolBracketsBracesR.pattern),
	)

	val NumberPattern : Pattern<Number> = sequence1(tupleOf(Tokens.PrimitiveNumber.pattern)) { (number) ->
		val type = when (number.content[1]) {
			'd' -> 10
			'b' -> 2
			'x' -> 16
			else -> throw IllegalStateException()
		}
		val num = number.content.substring(2)
		object : Number { override val value: Long = num.toLong(type) }
	}

	val BooleanPattern : Pattern<Boolean> = sequence1(tupleOf(Tokens.PrimitiveBoolean.pattern)) { (bool) ->
		object : Boolean { override val value: kotlin.Boolean = bool.content.toBoolean() }
	}

	val StringPattern : Pattern<String> = sequence1(tupleOf(Tokens.PrimitiveString.pattern)) { (stringToken) ->
		val string = stringToken.content
		object : String { override val value: kotlin.String = string.substring(1, string.length - 1) }
	}

	val CharPattern : Pattern<Char> = sequence1(tupleOf(Tokens.PrimitiveChar.pattern)) { (char) ->
		object : Char {
			override val value: kotlin.Char = char.content[1]
		}
	}

	val PrimitivePattern : Pattern<Primitive> = superPattern(NumberPattern, BooleanPattern, StringPattern, CharPattern)

	val FunctionCallPattern : Pattern<FunctionCall> = sequence2(
		tupleOf(IdentifierPattern, ArgumentsPattern)
	) { (name, arguments) -> FunctionCallImpl(name, arguments) }

	val VariableCallPattern : Pattern<VariableCall> = sequence1(
		tupleOf(IdentifierPattern)
	) { VariableCallImpl(it.component1()) }

	val ExpressionPattern : Pattern<Expression> =
		superPattern(VariableCallPattern, FunctionCallPattern, PrimitivePattern)

	val RETURN_STATEMENT_PATTERN : Pattern<ReturnStatement> = sequence2(
		tupleOf(Tokens.KeywordReturn.pattern, ExpressionPattern)
	) { object : ReturnStatement {
		override val expression: Expression = it.component2()
	} }

	val AssignmentPattern : Pattern<Assignment> = sequence3(tupleOf(
		IdentifierPattern, Tokens.SymbolEqualSign.pattern, ExpressionPattern
	)) { (name, _, expression) -> AssignmentImpl(name, expression) }

	val VariablePattern : Pattern<Variable> = sequence3(tupleOf(
		Tokens.KeywordVariable.pattern, IdentifierPattern, ReturnTypePattern
	)) { (_, name, returnType) -> object : Variable {
		override val name: kotlin.String = name
		override val returnType: Type = returnType
	} }

	val CodePattern : Pattern<List<Statement>> = scopePattern(
		elementPattern = superPattern(VariablePattern, AssignmentPattern, RETURN_STATEMENT_PATTERN, ExpressionPattern),
		separatorPattern = Tokens.SymbolSemiColon.pattern,
		limiterPatterns = tupleOf(Tokens.SymbolBracketsParenthesesL.pattern, Tokens.SymbolBracketsParenthesesR.pattern),
		requireTrailing = true
	)

	val ArgumentDefPattern : Pattern<Variable> =
		sequence2(tupleOf(IdentifierPattern, ReturnTypePattern)) { (name, returnType) -> object : Variable {
			override val name: kotlin.String = name
			override val returnType: Type = returnType
		} }

	val ArgumentDefsPattern : Pattern<List<Variable>> = scopePattern(
		elementPattern = ArgumentDefPattern,
		separatorPattern = Tokens.SymbolComma.pattern,
		limiterPatterns = tupleOf(Tokens.SymbolBracketsBracesL.pattern, Tokens.SymbolBracketsBracesR.pattern)
	)

	val FunctionPattern : Pattern<FunctionImplementation> = sequence5(tupleOf(
		Tokens.KeywordFunction.pattern, IdentifierPattern, ArgumentDefsPattern, ReturnTypePattern, CodePattern
	)) { (_, name, arguments, returnType, code) ->
		object : FunctionImplementation {
			override val name: kotlin.String = name
			override val arguments: List<Variable> = arguments
			override val returnType: Type = returnType
			override val code: List<Statement> = code
		}
	}

	val DefinitionsPattern : Pattern<List<FunctionImplementation>> = object : Pattern<List<FunctionImplementation>> {

		override fun match(tokens: AdvancedIterator<Token>): Match<List<FunctionImplementation>> {
			tokens.pushContext()
			val list = mutableListOf<FunctionImplementation>()
			while (true) {
				val elementMatch = FunctionPattern.match(tokens)
				if (elementMatch is NoMatch) {
					tokens.popContext()
					return NoMatch(NoMatch.Cause.PatternMissMatch(FunctionPattern, elementMatch.cause))
				}
				list.add(elementMatch.node)
				if (!tokens.hasNext()) break
			}
			tokens.clearContext()
			return ValidMatch(list)
		}

	}

}

data class FunctionCallImpl(val name: kotlin.String, override val arguments: List<Expression>) : FunctionCall {
	override lateinit var function: Function
	fun id() = buildString {
		append(name)
		arguments.forEach { append(it.returnType) }
	}
}

data class VariableCallImpl(val name: kotlin.String) : VariableCall {
	override lateinit var variable: Variable
}

data class AssignmentImpl(
	val name: kotlin.String,
	override val expression: Expression
) : Assignment {
	override lateinit var variable: Variable
}

fun parse(tokens: List<Token>): List<FunctionImplementation> = FileParser(Patterns.DefinitionsPattern).parse(tokens)
