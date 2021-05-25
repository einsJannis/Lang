package dev.einsjannis.compiler.llvm

sealed interface Type : GlobalIRElement {

	object VoidType : Type {

		override val name: String get() = "void"

		override fun generateNameIR() = name

		override fun generateIR(): String = throw UnsupportedOperationException()

	}

	class PointerType(
		private val child: Type
	) : Type {

		override val name: String get() = "${child.generateNameIR()}*"

		override fun generateNameIR(): String = name

		override fun generateIR(): String = throw UnsupportedOperationException()

	}

	class StructType(
		override val name: String,
		private val childTypes: MutableMap<String, Type> = mutableMapOf()
	) : Type {

		fun addChild(name: String, type: Type) {
			childTypes[name] = type
		}

		override fun generateIR(): String =
			"${generateNameIR()} = type {${childTypes.values.joinToString { it.generateNameIR() }}}"

	}

}
