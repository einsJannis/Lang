rootProject.name = "Lang"
include("shared", "lexerLib", "parserLib", "llvm", "smallCompiler", "compiler")
project(":shared").projectDir = File("compiler/lib/shared")
project(":lexerLib").projectDir = File("compiler/lib/lexer")
project(":parserLib").projectDir = File("compiler/lib/parser")
project(":llvm").projectDir = File("compiler/lib/llvm")
project(":smallCompiler").projectDir = File("compiler/smallImpl")
project(":compiler").projectDir = File("compiler/impl")
