rootProject.name = "Lang"
include("shared", "lexerLib", "parserLib", "compiler")
project(":shared").projectDir = File("compiler/lib/shared")
project(":lexerLib").projectDir = File("compiler/lib/lexer")
project(":parserLib").projectDir = File("compiler/lib/parser")
project(":compiler").projectDir = File("compiler/impl")
