rootProject.name = "Lang"
include("shared", "lexerLib", "parserLib", "llvm", "compiler")
project(":shared").projectDir = File("compiler/lib/shared")
project(":lexerLib").projectDir = File("compiler/lib/lexer")
project(":parserLib").projectDir = File("compiler/lib/parser")
project(":llvm").projectDir = File("compiler/lib/llvm")
project(":compiler").projectDir = File("compiler/impl")
