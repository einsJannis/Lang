package dev.einsjannis.gradle.kotlin

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.work.InputChanges
import java.io.File

abstract class TupleGeneratorTask : DefaultTask() {

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @get:Input
    abstract val amountOfTuples: Property<Int>

    @TaskAction
    fun execute(inputChanges: InputChanges) {
        val file = File("${outputDirectory.get().asFile.path}/dev/einsjannis/tuple.kt")
        if (!file.parentFile.exists()) file.parentFile.mkdirs()
        if (!file.exists()) file.createNewFile()
        val content = generate()
        file.writeText(content)
    }

    private fun generate(): String = """
        @file:Suppress("UNUSED")
        package dev.einsjannis
        ${generateBaseTuple()}${generateTuples()}
        ${generateTupleOfFuns()}
        ${generateGeneralPlusFun()}
        ${generatePlusFuns()}
        ${generateCastFuns()}
    """.trimIndent()

    //language=kotlin
    private fun generateGeneralPlusFun(): String = """
        fun <B : BASE, T : BASE, BASE> Tuple<B>.plus(value: T): Tuple<BASE> = when (this) {
            ${generatePlusFunMappings()}
            else -> throw UnsupportedOperationException()
        }
    """

    private fun generatePlusFunMappings(): String =
        (amountOfTuples.get() - 2).downTo(0).joinToString("\n            ") {
            generatePlusFunMapping(it)
        }

    private fun generatePlusFunMapping(index: Int) = "is Tuple$index<B, ${generateStars(index)}> -> this.plus(value)"

    private fun generateStars(index: Int): String = template(index, ", ") { "*" }

    private fun generateCastFuns(): String = template(amountOfTuples.get()) { generateCastFun(it) }

    //language=kotlin
    private fun generateCastFun(index: Int): String = """
        fun <${generateGenericsDef(index)}, T> Tuple$index<${generateGenerics(index)}>.castTo(constructor: (${
        generateGenerics(
            index,
            false
        )
    }) -> T): T = constructor(${generateValues(index)})
    """

    private fun generatePlusFuns(): String = template(amountOfTuples.get() - 1) { generatePlusFun(it) }

    private fun generateGenericsDefWithBase(index: Int) = generateGenericsDef(index, " : BASE")

    //language=kotlin
    private fun generatePlusFun(index: Int): String = """
        operator fun <${generateGenericsDefWithBase(index)}, T : BASE, BASE> ${generateSimpleTupleName(index)}.plus(value: T) = tupleOf(${
        generateValues(
            index
        ).let { if (it == "") it else "${it}, " }
    }value)
    """

    //language=kotlin
    private fun generateBaseTuple() = """
        interface Tuple<B> : Iterable<B>
    """

    private fun generateTuples(): String = template(amountOfTuples.get()) { generateTuple(it) }

    //language=kotlin
    private fun generateTuple(index: Int): String = """
        interface ${generateTupleName(index)} : Tuple<B> {
            ${generateValueDefs(index - 1)}${generateIteratorFun(index)}
        }
    """

    private fun generateTupleName(index: Int): String = "Tuple$index<${generateGenericsDef(index)}>"

    //language=kotlin
    private fun generateIteratorFun(index: Int): String = """
            override fun iterator(): Iterator<B> = listOf<B>(${generateValues(index)}).iterator()
    """

    private fun generateGenericsDef(index: Int, base: String = ""): String = template(index, ", ") { "T$it : B" }.let {
        if (index == 0) return@let "B$base" else return@let "B$base, $it"
    }

    private fun generateValueName(index: Int): String = "component${index + 1}()"

    private fun generateValueDefs(index: Int): String = template(index + 1) { generateValueDef(it) }

    //language=kotlin
    private fun generateValueDef(index: Int): String = if (index < 0) "" else """
            operator fun ${generateValueName(index)}: T${index}
    """

    private fun generateValues(index: Int): String = template(index, ", ") { generateValueName(it) }

    private fun generateTupleOfFuns(): String = template(amountOfTuples.get()) { generateTupleOfFun(it) }

    //language=kotlin
    private fun generateTupleOfFun(index: Int): String = """
        fun <${generateGenericsDef(index)}> tupleOf(${generateArgsDef(index)}): ${generateSimpleTupleName(index)} = object : ${
        generateSimpleTupleName(
            index
        )
    } {
            ${generateValuesImpl(index)}
        }
    """

    private fun generateSimpleTupleName(index: Int) = "Tuple$index<${generateGenerics(index)}>"

    private fun generateValuesImpl(index: Int): String = template(index) { generateValueImpl(it) }

    //language=kotlin
    private fun generateValueImpl(index: Int) = """
            override operator fun ${generateValueName(index)}: T$index = v$index
    """

    private fun generateGenerics(index: Int, includeBase: Boolean = true): String =
        template(index, ", ") { "T$it" }.let {
            if (!includeBase) it else if (index == 0) "B" else "B, $it"
        }

    private fun generateArgsDef(index: Int): String = template(index, ", ") { "v$it: T$it" }

}
