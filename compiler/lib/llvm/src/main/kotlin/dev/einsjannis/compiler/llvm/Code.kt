package dev.einsjannis.compiler.llvm

interface Code : IRElement {

	class Return(
		val value: Variable
	) : Code {

		override fun generateIR(): String = "ret ${value.type.generateNameIR()} ${value.generateNameIR()}"

	}

	class FunctionCall(
		val function: Function,
		val arguments: List<Variable>,
		override val name: String
	) : Code, IRElement.Named.Local {

		override val type: Type
			get() = function.returnType

		override fun generateIR(): String =
			"${generateNameIR()} = call ${function.returnType.generateNameIR()} ${function.generateNameIR()}(${arguments.joinToString { "${it.type.generateNameIR()} ${it.generateNameIR()}" }})"

	}

	class AllocCall(
		override val type: Type.BuiltIn.PointerType<Type>,
		override val name: String
	) : Code, IRElement.Named.Local {

		override fun generateIR(): String = "${generateNameIR()} = alloca ${type.child.generateNameIR()}"

	}

	/*class StructVariableCall(
		override val type: Type.StructType,
		val fieldName: String,
		val variable: Variable,
		override val name: String,
	) : Code, IRElement.Named.Local {

		override fun generateIR(): String {
			val inner = type.getChildByName(fieldName) ?: throw Exception("NoSuchField: $fieldName in ${type.name}")
			return "${generateNameIR()} = getelementptr ${type.generateNameIR()}, ${type.ptr().generateNameIR()} ${variable.generateNameIR()}, ${inner.second} ${inner.first}"
		}

	}*/

	class VarAlias(
		val variable: Variable,
		override val name: String
	) : Code, IRElement.Named.Local {

		override val type: Type get() = variable.type

		override fun generateIR(): String = "${generateNameIR()} = ${variable.generateNameIR()}"

	}

	class Primitive(
		primitiveValue: PrimitiveValue
	) : IRElement.Named.Local {

		override val type: Type = primitiveValue.type

		override val name: String = primitiveValue.asString()

		override fun generateNameIR(): String = name

		override fun generateIR(): String = name

	}

	class StoreCall(val from: Variable, val to: Variable) : Code {

		override fun generateIR(): String =
			"store ${from.type.generateNameIR()} ${from.generateNameIR()}, ${to.type.generateNameIR()} ${to.generateNameIR()}"

	}

	class Label(override val name: String) : Code, IRElement.Named.Label {

		override val type: Type = Type.BuiltIn.Label

		override fun generateIR(): String = "${generateNameIR()}:"

	}

	class IcmpCall(
		val operator: Operator,
		val op1: Variable,
		val op2: Variable,
		override val name: String
	) : Code, IRElement.Named.Local {

		enum class Operator {
			EQ,
			NE,
			UGT,
			UGE,
			ULT,
			ULE,
			SGT,
			SGE,
			SLT,
			SLE
		}

		override val type: Type = Type.BuiltIn.Number.Integer(1)

		override fun generateIR(): String =
			"${generateNameIR()} = icmp ${operator.name.lowercase()} ${op1.type.generateNameIR()} ${op1.generateNameIR()}, ${op2.generateNameIR()}"

	}

	class BrCall(val condition: Variable, val ifLabelName: String, val elseLabelName: String) : Code {

		override fun generateIR(): String = "br ${condition.type.generateNameIR()} ${condition.generateNameIR()}, label %$ifLabelName, label %$elseLabelName"

	}

	class LoadCall(override val name: String, val ptr: Variable, override val type: Type): Code, IRElement.Named.Local {

		override fun generateIR(): String = "${generateNameIR()} = load ${type.generateNameIR()}, ${ptr.type.generateNameIR()} ${ptr.generateNameIR()}"

	}

	class AddCall(val a: Variable, val b: Variable, override val name: String) : Code, IRElement.Named.Local {

		override val type: Type get() = a.type

		override fun generateIR(): String = "${generateNameIR()} = add ${type.generateNameIR()} ${a.generateNameIR()}, ${b.generateNameIR()}"

	}

	class SubCall(val a: Variable, val b: Variable, override val name: String) : Code, IRElement.Named.Local {

		override val type: Type get() = a.type

		override fun generateIR(): String = "${generateNameIR()} = sub ${type.generateNameIR()} ${a.generateNameIR()}, ${b.generateNameIR()}"

	}

	class MulCall(val a: Variable, val b: Variable, override val name: String) : Code, IRElement.Named.Local {

		override val type: Type get() = a.type

		override fun generateIR(): String = "${generateNameIR()} = mul ${type.generateNameIR()} ${a.generateNameIR()}, ${b.generateNameIR()}"

	}

	class SDivCall(val a: Variable, val b: Variable, override val name: String) : Code, IRElement.Named.Local {

		override val type: Type get() = a.type

		override fun generateIR(): String = "${generateNameIR()} = sdiv ${type.generateNameIR()} ${a.generateNameIR()}, ${b.generateNameIR()}"

	}

	class SRemCall(val a: Variable, val b: Variable, override val name: String) : Code, IRElement.Named.Local {

		override val type: Type get() = a.type

		override fun generateIR(): String = "${generateNameIR()} = srem ${type.generateNameIR()} ${a.generateNameIR()}, ${b.generateNameIR()}"

	}

	class ShlCall(val a: Variable, val b: Variable, override val name: String) : Code, IRElement.Named.Local {

		override val type: Type get() = a.type

		override fun generateIR(): String = "${generateNameIR()} = shl ${type.generateNameIR()} ${a.generateNameIR()}, ${b.generateNameIR()}"

	}

	class LShrCall(val a: Variable, val b: Variable, override val name: String) : Code, IRElement.Named.Local {

		override val type: Type get() = a.type

		override fun generateIR(): String = "${generateNameIR()} = lshr ${type.generateNameIR()} ${a.generateNameIR()}, ${b.generateNameIR()}"

	}

	class AndCall(val a: Variable, val b: Variable, override val name: String) : Code, IRElement.Named.Local {

		override val type: Type get() = a.type

		override fun generateIR(): String = "${generateNameIR()} = and ${type.generateNameIR()} ${a.generateNameIR()}, ${b.generateNameIR()}"

	}

	class OrCall(val a: Variable, val b: Variable, override val name: String) : Code, IRElement.Named.Local {

		override val type: Type get() = a.type

		override fun generateIR(): String = "${generateNameIR()} = or ${type.generateNameIR()} ${a.generateNameIR()}, ${b.generateNameIR()}"

	}

	class XOrCall(val a: Variable, val b: Variable, override val name: String) : Code, IRElement.Named.Local {

		override val type: Type get() = a.type

		override fun generateIR(): String = "${generateNameIR()} = xor ${type.generateNameIR()} ${a.generateNameIR()}, ${b.generateNameIR()}"

	}

	class BitCast(val a: Variable, override val type: Type, override val name: String) : Code, IRElement.Named.Local {

		override fun generateIR(): String = "${generateNameIR()} = bitcast ${a.type.generateNameIR()} ${a.generateNameIR()} to ${type.generateNameIR()}"

	}

	class Zext(val a: Variable, override val type: Type, override val name: String) : Code, IRElement.Named.Local {

		override fun generateIR(): String = "${generateNameIR()} = zext ${a.type.generateNameIR()} ${a.generateNameIR()} to ${type.generateNameIR()}"

	}

	class Trunc(val a: Variable, override val type: Type, override val name: String) : Code, IRElement.Named.Local {

		override fun generateIR(): String = "${generateNameIR()} = trunc ${a.type.generateNameIR()} ${a.generateNameIR()} to ${type.generateNameIR()}"

	}

	class UBrCall(val label: String): Code {

		override fun generateIR(): String = "br label %$label"

	}

}
