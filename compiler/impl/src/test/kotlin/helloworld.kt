import dev.einsjannis.lang.compiler.compile
import java.nio.file.Path

fun main() {
	println(compile(Path.of(".", "example", "helloworld.lang")))
}
