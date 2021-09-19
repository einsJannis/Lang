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

	}

}
