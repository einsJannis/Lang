package dev.einsjannis.compiler.llvm

sealed interface Type : IRElement.Named.Global {

	interface BuiltIn : Type {

		override val type: Type get() = throw UnsupportedOperationException()

		override fun generateNameIR(): String = name

		override fun generateIR(): String = throw UnsupportedOperationException()

		object VoidType : BuiltIn {

			override val name: String get() = "void"

		}

		interface Number : BuiltIn {

			class Integer(val n: Int) : Number {

				override val name: String get() = "i$n"

			}

		}

		class PointerType<T : Type>(
			private val child: T
		) : BuiltIn {

			override val name: String get() = "${child.generateNameIR()}*"

		}

		class FunctionType(
			private val function: Function
		) : BuiltIn {

			override val name: String get() = "${function.returnType.generateNameIR()} (${function.arguments.joinToString { it.type.generateNameIR() }})"

		}

		class Array<T : Type>(
			private val child: T,
			private val size: Int
		) : BuiltIn {
			override val name: String get() = "[$size x ${child.generateNameIR()}]"
		}

	}

	/*class StructType(
		override val name: String,
		private val childTypes: MutableList<Pair<String, Type>> = mutableListOf()
	) : Type {

		override val type: Type
			get() = TODO("Not yet implemented")

		fun addChild(name: String, type: Type) {
			childTypes += Pair(name, type)
		}

		fun getChildByName(name: String) : Pair<Int, Type>? {
			childTypes.forEachIndexed { i, v ->
				if (v.first == name) return Pair(i, v.second)
			}
			return null
		}

		override fun generateIR(): String =
			"${generateNameIR()} = type {${childTypes.joinToString { it.second.generateNameIR() }}}"

	}*/

}
