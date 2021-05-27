package dev.einsjannis.compiler.llvm

sealed interface Type : GlobalIRElement {

	interface BuiltIn : Type {

		override fun generateNameIR(): String = name

		override fun generateIR(): String = throw UnsupportedOperationException()

		object VoidType : BuiltIn {

			override val name: String get() = "void"

		}

		interface Number : BuiltIn {

			object I1 : Number {

				override val name: String get() = "i1"

			}

			object I8 : Number {

				override val name: String get() = "i8"

			}

			object I16 : Number {

				override val name: String get() = "i16"

			}

			object I32 : Number {

				override val name: String get() = "i32"

			}

			object I64 : Number {

				override val name: String get() = "i64"

			}

			object I128 : Number {

				override val name: String get() = "i128"

			}

		}

		class PointerType<T : Type>(
			private val child: T
		) : BuiltIn {

			override val name: String get() = "${child.generateNameIR()}*"

		}

	}

	class StructType(
		override val name: String,
		private val childTypes: MutableList<Pair<String, Type>> = mutableListOf()
	) : Type {

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

	}

}
