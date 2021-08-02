package dev.einsjannis

class Dafuq : Exception(message = "how did we end up here?")

@Suppress("NOTHING_TO_INLINE")
inline fun yeet(throwable: Throwable): Nothing = throw throwable
