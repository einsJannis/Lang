package dev.einsjannis.lang.compiler.semanticAnalysis

import dev.einsjannis.lang.compiler.ir.*
import dev.einsjannis.lang.compiler.ir.builtin.Functions
import dev.einsjannis.lang.compiler.ir.builtin.Types
import dev.einsjannis.lang.compiler.ir.builtin.Variables
import dev.einsjannis.lang.compiler.parser.internal.FunctionCallImpl
import dev.einsjannis.lang.compiler.parser.internal.ReturnTypeImpl
import dev.einsjannis.lang.compiler.parser.internal.VariableCallImpl
import dev.einsjannis.lang.compiler.semanticAnalysis.exception.*

object SemanticAnalyser {

    fun analyse(tree: DefinitionScope) {
        val globalStructs: MutableList<TypeDefinition> = Types.all.toMutableList()
        val globalVariables: MutableList<VariableDefinition> = Variables.all.toMutableList()
        val globalFunctions: MutableList<FunctionDefinition> = Functions.all.toMutableList()
        for (definition in tree.children) {
            when (definition) {
                is StructDefinition                 -> {
                    analyseStruct(definition, globalStructs, globalVariables, globalFunctions)
                    globalStructs += definition
                }
                is VariableDefinition               -> {
                    analyseVariable(definition, globalStructs, globalVariables, globalFunctions)
                    globalVariables += definition
                }
                is FunctionImplementationDefinition -> {
                    analyseFunction(definition, globalStructs, globalVariables, globalFunctions)
                    globalFunctions += definition
                }
            }
        }
    }

    private fun analyseStruct(
        structDefinition: StructDefinition,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>,
        scopeFunctions: List<FunctionDefinition>
    ) {
        val variables: MutableList<VariableDefinition> = mutableListOf()
        analyseIdentifier(structDefinition, structDefinition.name, scopeStructs, scopeVariables, scopeFunctions)
        structDefinition.variableDefinitions.children.forEach {
            analyseStructVariable(it, scopeStructs, variables)
            variables += it
        }
    }

    private fun analyseStructVariable(
        variable: VariableDefinition,
        globalStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>
    ) {
        analyseIdentifier(variable, variable.name, scopeVariables)
        analyseReturnType(variable.returnType, globalStructs)
    }

    private fun analyseIdentifier(definition: Definition, identifier: String, vararg definitions: List<Definition>) {
        val a = definitions.mapNotNull { list -> list.find { it.name == identifier } }.firstOrNull()
        if (a != null)
            throw NonUniqueIdentifierException(identifier, a, definition)
    }

    private fun analyseVariable(
        variableDefinition: VariableDefinition,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>,
        scopeFunctions: List<FunctionDefinition>
    ) {
        analyseIdentifier(variableDefinition, variableDefinition.name, scopeStructs, scopeVariables, scopeFunctions)
        analyseReturnType(variableDefinition.returnType, scopeStructs)
        variableDefinition.initialization?.let {
            analyseExpression(it, scopeStructs, scopeVariables, scopeFunctions)
            checkType(variableDefinition.returnType, it.returnType) { a, b ->
                TypeMissMatchInVariableDefinition(variableDefinition, a, b)
            }
        }
    }

    private fun analyseReturnType(returnType: ReturnType, scopeStructs: List<TypeDefinition>) {
        if (returnType is ReturnTypeImpl)
            returnType.typeDefinition = scopeStructs.find { it.name == returnType.name }
                ?: throw UnknownReferenceException(returnType.name)
    }

    private fun analyseFunction(
        functionDefinition: FunctionImplementationDefinition,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>,
        scopeFunctions: List<FunctionDefinition>
    ) {
        analyseIdentifier(functionDefinition, functionDefinition.name, scopeStructs, scopeVariables, scopeFunctions)
        analyseReturnType(functionDefinition.returnType, scopeStructs)
        val mScopeVariables = scopeVariables.toMutableList()
        functionDefinition.arguments.children.forEach {
            analyseArgument(it, scopeStructs, scopeVariables)
            mScopeVariables.add(it)
        }
        functionDefinition.code.children.forEach {
            analyseCode(
                it,
                scopeStructs,
                mScopeVariables,
                listOf(functionDefinition).plus(scopeFunctions),
                functionDefinition
            )
        }
    }

    private fun analyseArgument(
        argumentDefinition: ArgumentDefinition,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>
    ) {
        analyseIdentifier(argumentDefinition, argumentDefinition.name, scopeVariables)
        analyseReturnType(argumentDefinition.returnType, scopeStructs)
    }

    private fun analyseCode(
        code: Code,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>,
        scopeFunctions: List<FunctionDefinition>,
        enclosingFunction: FunctionDefinition
    ) {
        val mScopeVariables: MutableList<VariableDefinition> = scopeVariables.toMutableList()
        when (code) {
            is Expression          -> analyseExpression(code, scopeStructs, mScopeVariables, scopeFunctions)
            is ConditionStatement  -> analyseConditionStatement(
                code,
                scopeStructs,
                mScopeVariables,
                scopeFunctions,
                enclosingFunction
            )
            is VariableDefinition  -> {
                analyseVariable(code, scopeStructs, mScopeVariables, scopeFunctions)
                mScopeVariables.add(code)
            }
            is AssignmentStatement -> analyseAssignment(code, scopeStructs, mScopeVariables, scopeFunctions)
            is ReturnStatement     -> analyseReturnStatement(
                code,
                scopeStructs,
                mScopeVariables,
                scopeFunctions,
                enclosingFunction
            )
        }
    }

    private fun analyseConditionStatement(
        conditionStatement: ConditionStatement,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>,
        scopeFunctions: List<FunctionDefinition>,
        enclosingFunction: FunctionDefinition
    ) {
        analyseExpression(conditionStatement.condition, scopeStructs, scopeVariables, scopeFunctions)
        conditionStatement.code.children.forEach {
            analyseCode(it, scopeStructs, scopeVariables, scopeFunctions, enclosingFunction)
        }
    }

    private fun analyseReturnStatement(
        returnStatement: ReturnStatement,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>,
        scopeFunctions: List<FunctionDefinition>,
        enclosingFunction: FunctionDefinition
    ) {
        analyseExpression(returnStatement.expression, scopeStructs, scopeVariables, scopeFunctions)
        checkType(returnStatement.expression.returnType, enclosingFunction.returnType) { a, b ->
            TypeMissMatchInReturnStatement(returnStatement, a, b)
        }
    }

    private fun analyseAssignment(
        assignment: AssignmentStatement,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>,
        scopeFunctions: List<FunctionDefinition>
    ) {
        analyseVariableCall(assignment.variableCall, scopeStructs, scopeVariables, scopeFunctions)
        analyseExpression(assignment.expression, scopeStructs, scopeVariables, scopeFunctions)
        checkType(assignment.variableCall.returnType, assignment.expression.returnType) { a, b ->
            TypeMissMatchInAssignment(assignment, a, b)
        }
    }

    private fun analyseExpression(
        expression: Expression,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>,
        scopeFunctions: List<FunctionDefinition>
    ): Unit = when (expression) {
        is VariableCall -> analyseVariableCall(expression, scopeStructs, scopeVariables, scopeFunctions)
        is FunctionCall -> analyseFunctionCall(expression, scopeStructs, scopeVariables, scopeFunctions)
        is Cast         -> analyseCast(expression, scopeStructs, scopeVariables, scopeFunctions)
        else            -> Unit
    }

    private fun analyseCast(
        cast: Cast,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>,
        scopeFunctions: List<FunctionDefinition>
    ) {
        analyseExpression(cast.expression, scopeStructs, scopeVariables, scopeFunctions)
        analyseReturnType(cast.returnType, scopeStructs)
    }

    private fun analyseVariableCall(
        variableCall: VariableCall,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>,
        scopeFunctions: List<FunctionDefinition>
    ) {
        if (variableCall !is VariableCallImpl) return
        if (variableCall.parent != null) {
            analyseExpression(variableCall.parent!!, scopeStructs, scopeVariables, scopeFunctions)
            val type = variableCall.parent!!.returnType.typeDefinition
            if (type !is StructDefinition) throw UnknownReferenceException(variableCall.name)
            variableCall.variableDefinition =
                type.variableDefinitions.children.find { it.name == variableCall.name }
                    ?: throw UnknownReferenceException(variableCall.name)
        } else {
            variableCall.variableDefinition = scopeVariables.find { it.name == variableCall.name }
                ?: throw UnknownReferenceException(variableCall.name)
        }
    }

    private fun analyseFunctionCall(
        functionCall: FunctionCall,
        scopeStructs: List<TypeDefinition>,
        scopeVariables: List<VariableDefinition>,
        scopeFunctions: List<FunctionDefinition>
    ) {
        if (functionCall is FunctionCallImpl)
            functionCall.functionDefinition =
                scopeFunctions.find { it.name == functionCall.name }
                    ?: throw UnknownReferenceException(functionCall.name)
        functionCall.arguments.children.forEachIndexed { index, expression ->
            analyseExpression(expression, scopeStructs, scopeVariables, scopeFunctions)
            checkType(
                functionCall.functionDefinition.arguments.children[index].returnType,
                expression.returnType
            ) { a, b ->
                TypeMissMatchInFunctionArguments(functionCall, a, b)
            }
        }
    }

    private fun checkType(
        require: ReturnType,
        found: ReturnType,
        exceptionInit: (ReturnType, ReturnType) -> TypeMissMatch
    ) {
        if (require.isPointer != found.isPointer || require.typeDefinition.name != found.typeDefinition.name)
            throw exceptionInit(require, found)
    }

}
