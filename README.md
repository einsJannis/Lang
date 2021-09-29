# Lang

This is a compiler I developed for my own programming language.
Both were developed as part of my high school thesis.

If you want examples of this language take a look into [this folder](example)

## How to use

### Prerequisites

First, grab yourself an executable, either by [compiling it yourself] or by [downloading a zip from releases].
Make sure that you have the lastest version of Java installed.
Then install a llvm compiler such as [clang].

### Program usage

You can use this program to compile my Language to LLVM IR Code like so:
```bash
Lang <source files...>
```

If you want to get an executable you have to compile the output of the compiler using a llvm compiler such as [clang]:
```bash
clang <produced llvm ir file>
```

Afterwards you can execute or do whatever you'd like with the produced `a.out` file.

## Compiling it yourself

To compile this project yourself, grab the source code like so:
```bash
# Either git clone it
git clone https://github.com/einsJannis/Lang && cd Lang
# Or grab the source directly
curl -L https://github.com/einsJannis/Lang/archive/master.tar.gz --output - | tar xzf - && cd Lang-master
```

Then compile it like so:
```bash
# Either install it to ./build/install/Lang/bin/Lang
./gradlew installDist
# Or get a zip of the install
./gradlew distZip
# Or if you prefer to not compress your stuff
./gradlew distTar
```

Or directly execute it using:
```bash
./gradlew run <args>
```

[compiling yourself]: #compiling-it-yourself
[clang]: https://clang.llvm.org/
[downloading a zip from releases]: https://github.com/einsJannis/Lang/releases/latest
