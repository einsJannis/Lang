package dev.einsjannis.compiler.llvm

interface IRElement {

	fun generateIR(): String

	interface Named : IRElement {

		val name: String

		val type: Type

		fun generateNameIR(): String

		interface Local : Named {

			override fun generateNameIR(): String = "%$name"

		}

		interface Global : Named {

			override fun generateNameIR(): String = "@$name"

		}

		interface Label : Named {

			override fun generateNameIR(): String = name

		}

		object Null : Named {

			override val name: String = "null"

			override val type: Type = Type.BuiltIn.Number.Integer(8).ptr()

			override fun generateNameIR(): String = name

			override fun generateIR(): String = name

		}

	}

}
