package dev.einsjannis.compiler.llvm

import org.sosy_lab.llvm_j.binding.LLVMLibrary

class Type internal constructor(internal val ref: LLVMLibrary.LLVMTypeRef) {

}
