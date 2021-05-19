package dev.einsjannis.compiler.parser

import dev.einsjannis.compiler.lexer.Token
import dev.einsjannis.compiler.lexer.TokenType
import kotlin.reflect.KClass

sealed class Match<out T : Any?> {

    abstract val node: T

}

data class ValidMatch<out T : Any?>(override val node: T) : Match<T>()

data class NoMatch(val cause: Cause) : Match<Nothing>() {

    inline fun <reified T : Cause> causedBy() = causedBy(T::class)

    fun causedBy(klass: KClass<out Cause>): Boolean = if (klass.isInstance(klass)) true else cause.causedBy(klass)

    override val node: Nothing get() = throw NoSuchElementException()

    sealed class Cause {

        open fun causedBy(klass: KClass<out Cause>): Boolean = false

        data class NoTokensLeft(val expected: TokenType) : Cause()

        data class TokenMissMatch(val expected: TokenType, val found: Token) : Cause()

        data class PatternMissMatch(val expected: Pattern<*>, val cause: Cause) : Cause() {

            override fun causedBy(klass: KClass<out Cause>): Boolean =
                if (klass.isInstance(cause)) true else cause.causedBy(klass)

        }

        class PatternMissMatches(list: List<PatternMissMatch>) : Cause(), List<PatternMissMatch> by list {

            override fun causedBy(klass: KClass<out Cause>): Boolean = this.any {
                if (klass.isInstance(it)) true else it.causedBy(klass)
            }

        }

    }

}
