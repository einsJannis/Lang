package dev.einsjannis.compiler.parser

class NoMatchException(val noMatch: NoMatch) : Exception() {

    override val message: String get() = TODO() //"Matches failed: ${noMatch.cause.info}"

}
