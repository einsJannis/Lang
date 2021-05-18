import dev.einsjannis.AdvancedIterator
import dev.einsjannis.Tuple2
import dev.einsjannis.castTo
import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.compiler.lexer.TokenType
import dev.einsjannis.tupleOf

interface Pattern<out T> {

    fun match(tokens: AdvancedIterator<Token>): Match<T>

}

fun <T> AdvancedIterator<Token>.match(pattern: Pattern<T>): Match<T> {
    //println("Matching for ${pattern.name}")
    val match = pattern.match(this)
    //println("${pattern.name}: ${if (match is NoMatch) "NoMatch" else "Match"}")
    return match
}

class TokenPattern(val tokenType: TokenType) : Pattern<Token> {

    override fun match(tokens: AdvancedIterator<Token>): Match<Token> {
        if (!tokens.hasNext()) return NoMatch(NoMatch.Cause.NoTokensLeft(tokenType))
        val token = tokens.next()
        if (token.type != tokenType) {
            tokens.previous()
            return NoMatch(NoMatch.Cause.TokenMissMatch(tokenType, token))
        }
        return ValidMatch(token)
    }

}

val TokenType.pattern: Pattern<Token> get() = TokenPattern(this)

class OptionalPattern<T>(val pattern: Pattern<T>) : Pattern<T?> {

    override fun match(tokens: AdvancedIterator<Token>): Match<T?> {
        val match = tokens.match(pattern)
        if (match is NoMatch) {
            if (match.causedBy<NoMatch.Cause.NoTokensLeft>())
                return NoMatch(NoMatch.Cause.PatternMissMatch(pattern, match.cause))
            return ValidMatch(null)
        }
        return ValidMatch(match.node)
    }

}

class LazyPattern<T>(val lazyPattern: () -> Pattern<T>) : Pattern<T> {

    override fun match(tokens: AdvancedIterator<Token>): Match<T> =
        tokens.match(lazyPattern())

}

fun <T> lazyPatternMap(lazyPattern: () -> Pattern<T>) = LazyPattern(lazyPattern)

fun <T> optional(pattern: Pattern<T>) = OptionalPattern(pattern)

fun <T> superPattern(patterns: List<Pattern<T>>) = object : Pattern<T> {

    override fun match(tokens: AdvancedIterator<Token>): Match<T> {
        val failed = mutableListOf<Tuple2<Any, Pattern<T>, NoMatch.Cause>>()
        for (pattern in patterns) {
            val match = tokens.match(pattern)
            if (match is NoMatch) {
                failed.add(tupleOf(pattern, match.cause))
                continue
            }
            return match
        }
        return NoMatch(NoMatch.Cause.PatternMissMatches(failed.map { it.castTo(NoMatch.Cause::PatternMissMatch) }))
    }

}

fun <E : Any, S : Any, LS, LE, T> scopePattern(
    elementPattern: Pattern<E>,
    separatorPattern: Pattern<S>,
    limiterPatterns: Tuple2<Pattern<*>, Pattern<LS>, Pattern<LE>>,
    constructor: (list: List<E>) -> T,
    requireTrailing: Boolean = false
) = object : Pattern<T> {

    val startPattern = limiterPatterns.component1()
    val endPattern = limiterPatterns.component2()

    override fun match(tokens: AdvancedIterator<Token>): Match<T> {
        tokens.pushContext()
        val startMatch = tokens.match(startPattern)
        if (startMatch is NoMatch) {
            tokens.popContext()
            return NoMatch(NoMatch.Cause.PatternMissMatch(startPattern, startMatch.cause))
        }
        val list = buildList {
            while (true) {
                val elementMatch = tokens.match(elementPattern)
                if (elementMatch is NoMatch) {
                    if (tokens.match(endPattern) is ValidMatch) break
                    tokens.popContext()
                    return NoMatch(NoMatch.Cause.PatternMissMatch(elementPattern, elementMatch.cause))
                }
                add(elementMatch.node)
                val separatorMatch = tokens.match(separatorPattern)
                val endMatch = tokens.match(endPattern)
                if (separatorMatch is NoMatch && requireTrailing) {
                    tokens.popContext()
                    return NoMatch(
                        NoMatch.Cause.PatternMissMatch(
                            separatorPattern,
                            separatorMatch.cause
                        )
                    )
                }
                if (endMatch !is NoMatch) break
                if (separatorMatch is NoMatch) {
                    tokens.popContext()
                    return NoMatch(NoMatch.Cause.PatternMissMatch(endPattern, endMatch.cause))
                }
            }
        }
        tokens.removeContext()
        return ValidMatch(constructor(list))
    }

}
