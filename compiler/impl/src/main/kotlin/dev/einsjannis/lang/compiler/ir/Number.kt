package dev.einsjannis.lang.compiler.ir

interface Number : Primitive {

    override val bytes: ByteArray

    override val returnType: ReturnType

}
