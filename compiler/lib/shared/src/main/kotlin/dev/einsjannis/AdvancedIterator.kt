@file:Suppress("UNUSED")

package dev.einsjannis

import java.util.Stack
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class AdvancedIterator<T>(val list: List<T>) : ListIterator<T> {

    private var index: Int = 0

    private val stack: Stack<Int> = Stack()

    override fun hasNext(): Boolean = list.size > index

    override fun hasPrevious(): Boolean = 1 < index

    override fun next(): T = list[index].also { index++ }

    override fun previous(): T = list[--index]

    override fun nextIndex(): Int = index

    override fun previousIndex(): Int = index - 1

    fun peek(): T = list[index]

    fun pushContext() {
        stack.push(index)
    }

    fun popContext() {
        index = stack.pop()
    }

    fun clearContext() {
        stack.pop()
    }

    fun <T> inContext(block: () -> T): T {
        contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
        try {
            pushContext()
            return block()
        } finally {
            popContext()
        }
    }

}
