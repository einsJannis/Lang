package dev.einsjannis.compiler.llvm

fun <T : Type> T.ptr(): Type.BuiltIn.PointerType<T> = Type.BuiltIn.PointerType(this)
