package dev.einsjannis.compiler.llvm

fun <T : Type> T.ptr(): Type.BuiltIn.PointerType<T> = Type.BuiltIn.PointerType(this)

fun <T : Type> T.array(size: Int): Type.BuiltIn.Array<T> = Type.BuiltIn.Array(this, size)

typealias Variable = IRElement.Named

object Null : IRElement.Named {

	override val name: String = "null"

	override val type: Type = Type.BuiltIn.Number.Integer(8).ptr()

	override fun generateNameIR(): String = name

	override fun generateIR(): String = name

}
