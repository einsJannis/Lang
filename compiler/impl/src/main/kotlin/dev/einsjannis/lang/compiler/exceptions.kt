package dev.einsjannis.lang.compiler

class DeclarationClash(new: Declaration, old: Declaration) : Exception() {
	val Declaration.declarationTypeName get() = when (this) {
		is Function -> "function"
		is Variable -> "variable"
		is Type     -> "type"
	}
	override val message: kotlin.String = "The ${new.declarationTypeName} with the id ${new.id()} id already defined as a ${old.declarationTypeName}"
}

class UnknownRefException(ref: kotlin.String, type: kotlin.String) : Exception("Unknown $type with name $ref")

class ReturnTypeMissMatch(function: Function, returnStatement: ReturnStatement) : Exception("Tried to return ${returnStatement.expression} in function ${function.id()}")
