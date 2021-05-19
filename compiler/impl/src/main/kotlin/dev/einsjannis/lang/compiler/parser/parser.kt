package dev.einsjannis.lang.compiler.parser

import dev.einsjannis.AdvancedIterator
import dev.einsjannis.compiler.parser.*
import dev.einsjannis.lang.compiler.Token
import dev.einsjannis.lang.compiler.parser.internal.*
import dev.einsjannis.tupleOf
import dev.einsjannis.lang.compiler.ir.ArgumentDefinition as IRArgumentDefinition
import dev.einsjannis.lang.compiler.ir.ArgumentDefinitionScope as IRArgumentDefinitionScope
import dev.einsjannis.lang.compiler.ir.ArgumentScope as IRArgumentScope
import dev.einsjannis.lang.compiler.ir.AssignmentStatement as IRAssignmentStatement
import dev.einsjannis.lang.compiler.ir.Boolean as IRBoolean
import dev.einsjannis.lang.compiler.ir.Cast as IRCast
import dev.einsjannis.lang.compiler.ir.Char as IRChar
import dev.einsjannis.lang.compiler.ir.Code as IRCode
import dev.einsjannis.lang.compiler.ir.CodeScope as IRCodeScope
import dev.einsjannis.lang.compiler.ir.ConditionStatement as IRConditionStatement
import dev.einsjannis.lang.compiler.ir.Definition as IRDefinition
import dev.einsjannis.lang.compiler.ir.DefinitionScope as IRDefinitionScope
import dev.einsjannis.lang.compiler.ir.Expression as IRExpression
import dev.einsjannis.lang.compiler.ir.FunctionImplementationDefinition as IRFunctionImplDefinition
import dev.einsjannis.lang.compiler.ir.Number as IRNumber
import dev.einsjannis.lang.compiler.ir.Primitive as IRPrimitive
import dev.einsjannis.lang.compiler.ir.ReturnStatement as IRReturnStatement
import dev.einsjannis.lang.compiler.ir.ReturnType as IRReturnType
import dev.einsjannis.lang.compiler.ir.String as IRString
import dev.einsjannis.lang.compiler.ir.StructDefinition as IRStructDefinition
import dev.einsjannis.lang.compiler.ir.StructVariableDefinitionScope as IRStructVariableDefinitionScope
import dev.einsjannis.lang.compiler.ir.VariableDefinition as IRVariableDefinition

object Patterns {

    object Cast : Pattern<IRCast> by sequence4(
        tupleOf(Token.Symbol.Brackets.BracesL.pattern, sequence2(
            tupleOf(Token.Identifier.pattern, optional(Token.Symbol.Star.pattern)),
            { (identifier, star) -> ReturnTypeImpl(identifier, star != null) }
        ), Token.Symbol.Brackets.BracesR.pattern, Expression),
        { (_, returnType, _, expression) -> CastImpl(returnType, expression) }
    )

    object ConditionStatement : Pattern<IRConditionStatement> by sequence5(
        tupleOf(
            Token.Keyword.If.pattern,
            Token.Symbol.Brackets.BracesL.pattern,
            lazyPatternMap { Expression },
            Token.Symbol.Brackets.BracesR.pattern,
            lazyPatternMap { CodeScope }
        ),
        { (_, _, condition, _, code) -> ConditionStatementImpl(condition, code) }
    )

    object AssignmentStatement : Pattern<IRAssignmentStatement> by sequence3(
        tupleOf(VariableCall, Token.Symbol.Assign.pattern, Expression),
        { (call, _, expression) -> AssignmentStatementImpl(call, expression) }
    )

    object ReturnStatement : Pattern<IRReturnStatement> by sequence2(
        tupleOf(Token.Keyword.Return.pattern, Expression),
        { (_, expression) -> ReturnStatementImpl(expression) }
    )

    object Boolean : Pattern<IRBoolean> by sequence1(
        tupleOf(Token.Primitive.Boolean.pattern),
        { (token) -> BooleanImpl(token) }
    )

    object String : Pattern<IRString> by sequence1(
        tupleOf(Token.Primitive.String.pattern),
        { (token) -> StringImpl(token) }
    )

    object Char : Pattern<IRChar> by sequence1(
        tupleOf(Token.Primitive.Char.pattern),
        { (token) -> CharImpl(token) }
    )

    object Number : Pattern<IRNumber> by sequence1(
        tupleOf(Token.Primitive.Number.pattern),
        { (token) -> NumberImpl(token) }
    )

    object Primitive : Pattern<IRPrimitive> by superPattern(listOf(Number, Char, String, Boolean))

    private object InnerVariableCall : Pattern<VariableCallImpl?> by optional(sequence2(
        tupleOf(Token.Symbol.Dot.pattern, lazyPatternMap { VariableCall }),
        { (_, call) -> call }
    ))

    private object VariableCall : Pattern<VariableCallImpl> by sequence2(
        tupleOf(Token.Identifier.pattern, InnerVariableCall),
        { (identifier, other) ->
            if (other == null)
                VariableCallImpl(identifier, null)
            else {
                other.parent = VariableCallImpl(identifier, null)
                other
            }
        }
    )

    object ArgumentScope : Pattern<IRArgumentScope> by scopePattern(
        Expression,
        Token.Symbol.Comma.pattern,
        tupleOf(Token.Symbol.Brackets.BracesL.pattern, Token.Symbol.Brackets.BracesR.pattern),
        { ArgumentScopeImpl(it) },
        requireTrailing = false
    )

    object FunctionCall : Pattern<IRExpression> by sequence3(
        tupleOf(Token.Identifier.pattern, lazyPatternMap { ArgumentScope }, InnerVariableCall),
        { (identifier, arguments, other) ->
            if (other == null)
                FunctionCallImpl(identifier, arguments)
            else {
                other.parent = FunctionCallImpl(identifier, arguments)
                other
            }
        }
    )

    object Expression : Pattern<IRExpression> by superPattern(listOf(FunctionCall, VariableCall, Primitive))

    object Code :
        Pattern<IRCode> by superPattern(listOf(ReturnStatement, ConditionStatement, AssignmentStatement, Expression))

    object CodeScope : Pattern<IRCodeScope> by scopePattern(
        Code,
        Token.Symbol.SemiColon.pattern,
        tupleOf(Token.Symbol.Brackets.ParenthesesL.pattern, Token.Symbol.Brackets.ParenthesesR.pattern),
        { CodeScopeImpl(it) },
        requireTrailing = true
    )

    object ArgumentDefinition : Pattern<IRArgumentDefinition> by sequence2(
        tupleOf(Token.Identifier.pattern, ReturnType),
        { (identifier, returnType) -> ArgumentDefinitionImpl(identifier, returnType) }
    )

    object ArgumentDefinitionScope : Pattern<IRArgumentDefinitionScope> by scopePattern(
        ArgumentDefinition,
        Token.Symbol.Comma.pattern,
        tupleOf(Token.Symbol.Brackets.BracesL.pattern, Token.Symbol.Brackets.BracesR.pattern),
        { ArgumentDefinitionScopeImpl(it) },
    )

    object ReturnType : Pattern<IRReturnType> by sequence3(
        tupleOf(Token.Symbol.ReturnType.pattern, Token.Identifier.pattern, optional(Token.Symbol.Star.pattern)),
        { (_, identifier, star) -> ReturnTypeImpl(identifier, star != null) }
    )

    object StructVariableDefinitionScope : Pattern<IRStructVariableDefinitionScope> by scopePattern(
        VariableDefinition,
        Token.Symbol.SemiColon.pattern,
        tupleOf(Token.Symbol.Brackets.ParenthesesL.pattern, Token.Symbol.Brackets.ParenthesesR.pattern),
        { StructVariableDefinitionScopeImpl(it) },
        requireTrailing = true
    )

    object FunctionImplDefinition : Pattern<IRFunctionImplDefinition> by sequence5(
        tupleOf(
            Token.Keyword.Function.pattern,
            Token.Identifier.pattern,
            ArgumentDefinitionScope,
            ReturnType,
            CodeScope
        ),
        { (_, identifier, arguments, returnType, code) ->
            FunctionImplementationDefinitionImpl(
                identifier,
                arguments,
                returnType,
                code
            )
        }
    )

    object VariableDefinition : Pattern<IRVariableDefinition> by sequence4(
        tupleOf(Token.Keyword.Variable.pattern, Token.Identifier.pattern, ReturnType, optional(sequence2(
            tupleOf(Token.Symbol.Assign.pattern, Expression),
            { (_, expression) -> expression }
        ))),
        { (_, identifier, returnType, initialization) ->
            VariableDefinitionImpl(
                identifier,
                returnType,
                initialization
            )
        }
    )

    object StructDefinition : Pattern<IRStructDefinition> by sequence3(
        tupleOf(Token.Keyword.Struct.pattern, Token.Identifier.pattern, StructVariableDefinitionScope),
        { (_, identifier, structScope) -> StructDefinitionImpl(identifier, structScope) }
    )

    object Definition :
        Pattern<IRDefinition> by superPattern(listOf(StructDefinition, VariableDefinition, FunctionImplDefinition))

    object DefinitionScope : Pattern<IRDefinitionScope> {

        override fun match(tokens: AdvancedIterator<dev.einsjannis.compiler.lexer.Token>): Match<IRDefinitionScope> {
            tokens.pushContext()
            val list = buildList {
                while (true) {
                    val elementMatch = tokens.match(Definition)
                    if (elementMatch is NoMatch) {
                        tokens.popContext()
                        return NoMatch(NoMatch.Cause.PatternMissMatch(Definition, elementMatch.cause))
                    }
                    val separatorMatch = tokens.match(Token.Symbol.SemiColon.pattern)
                    if (separatorMatch is NoMatch) {
                        tokens.popContext()
                        return NoMatch(
                            NoMatch.Cause.PatternMissMatch(
                                Token.Symbol.SemiColon.pattern,
                                separatorMatch.cause
                            )
                        )
                    }
                    add(elementMatch.node)
                    if (!tokens.hasNext()) break
                }
            }
            tokens.removeContext()
            return ValidMatch(DefinitionScopeImpl(list))
        }

    }

}

private val parser = FileParser(Patterns.DefinitionScope)

fun parse(tokens: List<dev.einsjannis.compiler.lexer.Token>): IRDefinitionScope {
    return parser.parse(tokens)
}
