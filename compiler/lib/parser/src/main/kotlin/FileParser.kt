import dev.einsjannis.AdvancedIterator
import dev.einsjannis.compiler.lexer.Token

class FileParser<T>(private val rootPattern: Pattern<T>) {

    fun parse(tokens: List<Token>): T {
        val tokenIterator = AdvancedIterator(tokens)
        val match = rootPattern.match(tokenIterator)
        if (match is NoMatch)
            throw NoMatchException(match)
        return match.node
    }

}
