package dev.einsjannis.compiler.llvm

abstract class PrimitiveValue {
	abstract val type: Type
	abstract fun asString(): String
}
