package dev.einsjannis.compiler.parser

class NoMatchException(val noMatch: NoMatch) : Exception() {

    override val message: String get() = "Matches failed: ${noMatch.cause.info()}" //"Matches failed: ${noMatch.cause.info}"

}
