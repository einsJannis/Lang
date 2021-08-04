declare i32 printf(i8* noalias nocapture, ...)

define i32 println$string(i8* %arg0) {
	%tmp0 = call i32 (i8*, ...)* @printf(i8* "%s\n", i8* %arg0)
	ret %tmp0
}

define i32 @main() {
	call i32 (i8*)* @println(i8* "Hello world")
	ret 0
}
