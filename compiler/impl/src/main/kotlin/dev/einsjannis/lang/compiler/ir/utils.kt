package dev.einsjannis.lang.compiler.ir

fun DefinitionScope.printInfo() = println(info)

val DefinitionScope.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun DefinitionScope.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}Definitions {")
    children.forEach {
        when (it) {
            is StructDefinition   -> it.info(stringBuilder, "$indent  ")
            is VariableDefinition -> it.info(stringBuilder, "$indent  ")
            is FunctionDefinition -> it.info(stringBuilder, "$indent  ")
        }
    }
    stringBuilder.appendLine("$indent}")
}

val StructDefinition.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun StructDefinition.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}Struct {")
    stringBuilder.appendLine("$indent  name = $name")
    variableDefinitions.info(stringBuilder, "$indent  ")
    stringBuilder.appendLine("$indent}")
}

val StructVariableDefinitionScope.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun StructVariableDefinitionScope.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}Variables {")
    children.forEach { it.info(stringBuilder, "$indent  ") }
    stringBuilder.appendLine("$indent}")
}

val VariableDefinition.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun VariableDefinition.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}Variable {")
    stringBuilder.appendLine("$indent  name = $name")
    returnType.info(stringBuilder, "$indent  ")
    initialization?.let {
        stringBuilder.appendLine("$indent  Initialization {")
        it.info(stringBuilder, "$indent    ")
        stringBuilder.appendLine("$indent  }")
    }
    stringBuilder.appendLine("$indent}")
}

val ReturnType.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun ReturnType.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}ReturnType {")
    stringBuilder.appendLine("$indent  refName = ${typeDefinition.name}")
    stringBuilder.appendLine("$indent  isPointer = ${isPointer}")
    stringBuilder.appendLine("$indent}")
}

val Expression.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun Expression.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    when (this) {
        is FunctionCall -> {
            stringBuilder.appendLine("${indent}FunctionCall {")
            stringBuilder.appendLine("$indent  refName = ${functionDefinition.name}")
            arguments.info(stringBuilder, "$indent  ")
            returnType.info(stringBuilder, "$indent  ")
            stringBuilder.appendLine("$indent}")
        }
        is VariableCall -> {
            stringBuilder.appendLine("${indent}VariableCall {")
            stringBuilder.appendLine("$indent  refName = ${variableDefinition.name}")
            parent?.info(stringBuilder, "$indent  ")
            returnType.info(stringBuilder, "$indent  ")
            stringBuilder.appendLine("$indent}")
        }
        is Number       -> {
            stringBuilder.appendLine("${indent}Number {")
            stringBuilder.appendLine("$indent  value = ${bytes.toHexString()}")
            returnType.info(stringBuilder, "$indent  ")
            stringBuilder.appendLine("$indent}")
        }
        is Char         -> {
            stringBuilder.appendLine("${indent}Char {")
            stringBuilder.appendLine("$indent  value = $value")
            returnType.info(stringBuilder, "$indent  ")
            stringBuilder.appendLine("$indent}")
        }
        is String       -> {
            stringBuilder.appendLine("${indent}String {")
            stringBuilder.appendLine("$indent  value = $value")
            returnType.info(stringBuilder, "$indent  ")
            stringBuilder.appendLine("${indent}}")
        }
        is Boolean      -> {
            stringBuilder.appendLine("${indent}Boolean {")
            stringBuilder.appendLine("$indent  value = $value")
            returnType.info(stringBuilder, "$indent  ")
            stringBuilder.appendLine("$indent}")
        }
        is Cast         -> {
            stringBuilder.appendLine("${indent}Cast {")
            returnType.info(stringBuilder, "$indent  ")
            expression.info(stringBuilder, "$indent  ")
            stringBuilder.appendLine("}")
        }
        else            -> throw UnsupportedOperationException()
    }
}

private fun ByteArray.toHexString(): kotlin.String {
    return joinToString("") { it.toString(16).padStart(2, '0') }
}

val FunctionDefinition.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun FunctionDefinition.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}Function {")
    stringBuilder.appendLine("$indent  name = $name")
    arguments.info(stringBuilder, "$indent  ")
    returnType.info(stringBuilder, "$indent  ")
    if (this is FunctionImplementationDefinition) {
        code.info(stringBuilder, "$indent  ")
    }
    stringBuilder.appendLine("$indent}")
}

val ArgumentDefinitionScope.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun ArgumentDefinitionScope.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}Arguments {")
    children.forEach { it.info(stringBuilder, "$indent  ") }
    stringBuilder.appendLine("$indent}")
}

val ArgumentScope.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun ArgumentScope.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}Arguments {")
    children.forEach { it.info(stringBuilder, "$indent  ") }
    stringBuilder.appendLine("${indent}}")
}

val CodeScope.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun CodeScope.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}Code {")
    children.forEach {
        when (it) {
            is ConditionStatement  -> it.info(stringBuilder, "$indent  ")
            is Expression          -> it.info(stringBuilder, "$indent  ")
            is AssignmentStatement -> it.info(stringBuilder, "$indent  ")
            is ReturnStatement     -> it.info(stringBuilder, "$indent  ")
        }
    }
    stringBuilder.appendLine("${indent}}")
}

fun ConditionStatement.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}ConditionStatement {")
    stringBuilder.appendLine("$indent  Condition {")
    condition.info(stringBuilder, "$indent    ")
    stringBuilder.appendLine("$indent  }")
    code.info(stringBuilder, "$indent  ")
    stringBuilder.appendLine("$indent}")
}

val AssignmentStatement.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun AssignmentStatement.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}Assignment {")
    variableCall.info(stringBuilder, "$indent  ")
    expression.info(stringBuilder, "$indent  ")
    stringBuilder.appendLine("$indent}")
}

val ReturnStatement.info: kotlin.String
    get() {
        val builder = StringBuilder()
        info(builder)
        return builder.toString()
    }

fun ReturnStatement.info(stringBuilder: StringBuilder, indent: kotlin.String = "") {
    stringBuilder.appendLine("${indent}Return {")
    expression.info(stringBuilder, "$indent  ")
    stringBuilder.appendLine("$indent}")
}
