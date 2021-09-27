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

		abstract fun info(): String

        open fun causedBy(klass: KClass<out Cause>): Boolean = false

        data class NoTokensLeft(val expected: TokenType) : Cause() {
			override fun info(): String = "no tokens left, expected: ${expected.name}"
        }

        data class TokenMissMatch(val expected: TokenType, val found: Token) : Cause() {
			override fun info(): String = "token miss match, expected: ${expected.name} found: ${found.type.name}(${found.content})"
		}

        data class PatternMissMatch(val expected: Pattern<*>, val cause: Cause) : Cause() {

			override fun info(): String = "pattern miss match, expected ${expected.name} caused by: ${cause.info()}"

            override fun causedBy(klass: KClass<out Cause>): Boolean =
                if (klass.isInstance(cause)) true else cause.causedBy(klass)

        }

        class PatternMissMatches(list: List<PatternMissMatch>) : Cause(), List<PatternMissMatch> by list {

			override fun info(): String = joinToString { it.info() }

            override fun causedBy(klass: KClass<out Cause>): Boolean = this.any {
                if (klass.isInstance(it)) true else it.causedBy(klass)
            }

        }

    }

}
