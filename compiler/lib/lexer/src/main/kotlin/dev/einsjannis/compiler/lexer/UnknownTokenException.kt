package dev.einsjannis.compiler.lexer

import dev.einsjannis.Position2I
import java.nio.file.Path

class UnknownTokenException(val index: Int, val content: String, val path: Path) : RuntimeException() {

    val position: Position2I by lazy {
        val string = content.substring(0..index)
        val x = index - string.lastIndexOf('\n')
        val y = string.count { it == '\n' }
        Position2I(x, y)
    }

    val row: Int get() = position.y

    val column: Int get() = position.x

    val context: String by lazy {
        content.substring(index).let {
			val spaceIndex = it.indexOf(' ')
			if (spaceIndex != -1) it.substring(0, it.indexOf(' ')) else it
        }
    }

    override val message: String
        get() = "Unknown token found at [$row:$column] : \"$context\" : File(${path.toAbsolutePath()})"

}
