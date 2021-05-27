package dev.einsjannis.compiler.llvm

interface Code : IRElement {

	class Return(
		val value: NamedIRElement
	) : Code {

		override fun generateIR(): String = "ret $value"

	}

	class FunctionCall(
		val function: Function,
		val arguments: List<NamedIRElement>,
		override val name: String
	) : Code, LocalIRElement {

		override fun generateIR(): String =
			"${generateNameIR()} = call ${function.returnType.generateNameIR()} ${function.generateNameIR()}(${arguments.joinToString { it.generateNameIR() }})"

	}

	class AllocCall(
		val type: Type,
		override val name: String
	) : Code, LocalIRElement {

		override fun generateIR(): String = "${generateNameIR()} = alloca ${type.generateNameIR()}"

	}

	class StructVariableCall(
		val struct: Type.StructType,
		val fieldName: String,
		val variable: NamedIRElement,
		override val name: String,
	) : Code, LocalIRElement {

		override fun generateIR(): String {
			val inner = struct.getChildByName(fieldName) ?: throw Exception("NoSuchField: $fieldName in ${struct.name}")
			return "${generateNameIR()} = getelementptr ${struct.generateNameIR()}, ${struct.ptr().generateNameIR()} ${variable.generateNameIR()}, ${inner.second} ${inner.first}"
		}

	}

	class VarAlias(
		val variable: NamedIRElement,
		override val name: String
	) : Code, LocalIRElement {

		override fun generateIR(): String = "${generateNameIR()} = ${variable.generateNameIR()}"

	}

}
