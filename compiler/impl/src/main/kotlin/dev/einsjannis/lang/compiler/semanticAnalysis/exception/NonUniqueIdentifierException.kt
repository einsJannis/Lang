package dev.einsjannis.lang.compiler.semanticAnalysis.exception

import dev.einsjannis.lang.compiler.ir.*

class NonUniqueIdentifierException(val identifier: String, val first: Definition, val second: Definition) :
    Exception() {

    override val message: String
        get() = "Tried to register identifer $identifier multiple times:\n1:\n${infoOf(first)}2:\n${
            infoOf(
                second
            )
        }"

    fun infoOf(definition: Definition) = when (definition) {
        is VariableDefinition -> definition.info
        is FunctionDefinition -> definition.info
        is StructDefinition   -> definition.info
        else                  -> throw UnsupportedOperationException()
    }

}
