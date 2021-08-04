package dev.einsjannis.compiler.llvm

interface Code : IRElement {

	class Return(
		val value: Variable
	) : Code {

		override fun generateIR(): String = "ret $value"

	}

	class FunctionCall(
		val function: LocalFunction,
		val arguments: List<Variable>,
		override val name: String
	) : Code, IRElement.Named.Local {

		override val type: Type
			get() = function.returnType

		override fun generateIR(): String =
			"${generateNameIR()} = call ${function.returnType.generateNameIR()} ${function.generateNameIR()}(${arguments.joinToString { it.generateNameIR() }})"

	}

	class AllocCall(
		override val type: Type,
		override val name: String
	) : Code, IRElement.Named.Local {

		override fun generateIR(): String = "${generateNameIR()} = alloca ${type.generateNameIR()}"

	}

	class StructVariableCall(
		override val type: Type.StructType,
		val fieldName: String,
		val variable: Variable,
		override val name: String,
	) : Code, IRElement.Named.Local {

		override fun generateIR(): String {
			val inner = type.getChildByName(fieldName) ?: throw Exception("NoSuchField: $fieldName in ${type.name}")
			return "${generateNameIR()} = getelementptr ${type.generateNameIR()}, ${type.ptr().generateNameIR()} ${variable.generateNameIR()}, ${inner.second} ${inner.first}"
		}

	}

	class VarAlias(
		val variable: Variable,
		override val name: String
	) : Code, IRElement.Named.Local {

		override val type: Type get() = variable.type

		override fun generateIR(): String = "${generateNameIR()} = ${variable.generateNameIR()}"

	}

	class Primitive(
		val primitiveValue: PrimitiveValue,
		override val name: String
	) : Code, IRElement.Named.Local {

		override val type: Type = primitiveValue.type

		override fun generateIR(): String = "${generateNameIR()} = ${primitiveValue.asString()}"

	}

}
