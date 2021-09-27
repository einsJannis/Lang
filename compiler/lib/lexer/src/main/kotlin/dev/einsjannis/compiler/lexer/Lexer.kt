package dev.einsjannis.compiler.lexer

import java.nio.file.Path
import kotlin.contracts.contract
import kotlin.io.path.readText

class Lexer(private val tokenTypes: Iterable<TokenType>) {

	fun lex(path: Path): List<Token> {
		var index = 0
		val content = path.readText()
		return buildList {
			while (index < content.length) {
				index = nextToken(index, content, path)
			}
		}
	}

	private fun MutableList<Token>.nextToken(index: Int, content: String, path: Path): Int {
		for (tokenType in tokenTypes) {
			val match = tokenType.regex.find(content, index)
			if (isMatchValid(match, index)) {
				if (!tokenType.ignore) Token(tokenType, index, match.value, path).also { add(it) }
				return match.range.last + 1
			}
		}
		throw UnknownTokenException(index, content, path)
	}

	private fun isMatchValid(match: MatchResult?, index: Int): Boolean {
		contract { returns(true) implies (match != null) }
		return match != null && match.range.first == index
	}

}
