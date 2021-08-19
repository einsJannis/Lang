declare i32 printf(i8* noalias nocapture, ...)
define i32 @main() {
	%$tmp0 = "Hello World!"
	%$tmp1 = "%s\n"
	%$tmp2 = call i32 (i8*, ...)* @printf(i8* %$tmp1, i8* %$tmp0)
	%$tmp3 = 0
	ret %$tmp3
}
