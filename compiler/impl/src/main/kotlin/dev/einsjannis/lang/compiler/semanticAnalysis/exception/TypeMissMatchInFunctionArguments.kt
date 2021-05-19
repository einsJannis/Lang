package dev.einsjannis.lang.compiler.semanticAnalysis.exception

import dev.einsjannis.lang.compiler.ir.FunctionCall
import dev.einsjannis.lang.compiler.ir.ReturnType
import dev.einsjannis.lang.compiler.ir.info

class TypeMissMatchInFunctionArguments(
    val functionCall: FunctionCall,
    override val expected: ReturnType,
    override val found: ReturnType
) : TypeMissMatch() {

    override val message: String get() = "\nExpected: \n${expected.info}Found:\n${found.info}in:\n${functionCall.info}"

}
