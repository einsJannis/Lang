declare i16 @putchar(i16 %char)
define i8* @putChar$Byte(i8* %char) {
    %charValue = load i8, i8* %char
    %charInt = zext i8 %charValue to i16
    %ignored = call i16 @putchar(i16 %charInt)
    ret i8* null
}
declare i16 @getchar()
define i8* @getChar() {
    %charInt = call i16 @getchar()
    %result = alloca i8
    %char = trunc i16 %charInt to i8
    store i8 %char, i8* %result
    ret i8* %result
}
define i64* @add$Long$Long(i64* %arg0, i64* %arg1) {
    %argv0 = load i64, i64* %arg0
    %argv1 = load i64, i64* %arg1
    %result = add i64 %argv0, %argv1
    %resultPtr = alloca i64
    store i64 %result, i64* %resultPtr
    ret i64* %resultPtr
}
define i8* @add$Byte$Byte(i8* %arg0, i8* %arg1) {
    %argv0 = load i8, i8* %arg0
    %argv1 = load i8, i8* %arg1
    %result = add i8 %argv0, %argv1
    %resultPtr = alloca i8
    store i8 %result, i8* %resultPtr
    ret i8* %resultPtr
}
define i64* @sub$Long$Long(i64* %arg0, i64* %arg1) {
    %argv0 = load i64, i64* %arg0
    %argv1 = load i64, i64* %arg1
    %result = sub i64 %argv0, %argv1
    %resultPtr = alloca i64
    store i64 %result, i64* %resultPtr
    ret i64* %resultPtr
}
define i8* @sub$Byte$Byte(i8* %arg0, i8* %arg1) {
    %argv0 = load i8, i8* %arg0
    %argv1 = load i8, i8* %arg1
    %result = sub i8 %argv0, %argv1
    %resultPtr = alloca i8
    store i8 %result, i8* %resultPtr
    ret i8* %resultPtr
}
define i64* @mul$Long$Long(i64* %arg0, i64* %arg1) {
    %argv0 = load i64, i64* %arg0
    %argv1 = load i64, i64* %arg1
    %result = mul i64 %argv0, %argv1
    %resultPtr = alloca i64
    store i64 %result, i64* %resultPtr
    ret i64* %resultPtr
}
define i8* @mul$Byte$Byte(i8* %arg0, i8* %arg1) {
    %argv0 = load i8, i8* %arg0
    %argv1 = load i8, i8* %arg1
    %result = mul i8 %argv0, %argv1
    %resultPtr = alloca i8
    store i8 %result, i8* %resultPtr
    ret i8* %resultPtr
}
define i64* @div$Long$Long(i64* %arg0, i64* %arg1) {
    %argv0 = load i64, i64* %arg0
    %argv1 = load i64, i64* %arg1
    %result = sdiv i64 %argv0, %argv1
    %resultPtr = alloca i64
    store i64 %result, i64* %resultPtr
    ret i64* %resultPtr
}
define i8* @div$Byte$Byte(i8* %arg0, i8* %arg1) {
    %argv0 = load i8, i8* %arg0
    %argv1 = load i8, i8* %arg1
    %result = sdiv i8 %argv0, %argv1
    %resultPtr = alloca i8
    store i8 %result, i8* %resultPtr
    ret i8* %resultPtr
}
define i64* @rem$Long$Long(i64* %arg0, i64* %arg1) {
    %argv0 = load i64, i64* %arg0
    %argv1 = load i64, i64* %arg1
    %result = srem i64 %argv0, %argv1
    %resultPtr = alloca i64
    store i64 %result, i64* %resultPtr
    ret i64* %resultPtr
}
define i8* @rem$Byte$Byte(i8* %arg0, i8* %arg1) {
    %argv0 = load i8, i8* %arg0
    %argv1 = load i8, i8* %arg1
    %result = srem i8 %argv0, %argv1
    %resultPtr = alloca i8
    store i8 %result, i8* %resultPtr
    ret i8* %resultPtr
}
define i64* @shl$Long$Long(i64* %arg0, i64* %arg1) {
    %argv0 = load i64, i64* %arg0
    %argv1 = load i64, i64* %arg1
    %result = shl i64 %argv0, %argv1
    %resultPtr = alloca i64
    store i64 %result, i64* %resultPtr
    ret i64* %resultPtr
}
define i8* @shl$Byte$Byte(i8* %arg0, i8* %arg1) {
    %argv0 = load i8, i8* %arg0
    %argv1 = load i8, i8* %arg1
    %result = shl i8 %argv0, %argv1
    %resultPtr = alloca i8
    store i8 %result, i8* %resultPtr
    ret i8* %resultPtr
}
define i64* @shr$Long$Long(i64* %arg0, i64* %arg1) {
    %argv0 = load i64, i64* %arg0
    %argv1 = load i64, i64* %arg1
    %result = lshr i64 %argv0, %argv1
    %resultPtr = alloca i64
    store i64 %result, i64* %resultPtr
    ret i64* %resultPtr
}
define i8* @shr$Byte$Byte(i8* %arg0, i8* %arg1) {
    %argv0 = load i8, i8* %arg0
    %argv1 = load i8, i8* %arg1
    %result = lshr i8 %argv0, %argv1
    %resultPtr = alloca i8
    store i8 %result, i8* %resultPtr
    ret i8* %resultPtr
}
define i64* @and$Long$Long(i64* %arg0, i64* %arg1) {
    %argv0 = load i64, i64* %arg0
    %argv1 = load i64, i64* %arg1
    %result = and i64 %argv0, %argv1
    %resultPtr = alloca i64
    store i64 %result, i64* %resultPtr
    ret i64* %resultPtr
}
define i8* @and$Byte$Byte(i8* %arg0, i8* %arg1) {
    %argv0 = load i8, i8* %arg0
    %argv1 = load i8, i8* %arg1
    %result = and i8 %argv0, %argv1
    %resultPtr = alloca i8
    store i8 %result, i8* %resultPtr
    ret i8* %resultPtr
}
define i64* @or$Long$Long(i64* %arg0, i64* %arg1) {
    %argv0 = load i64, i64* %arg0
    %argv1 = load i64, i64* %arg1
    %result = or i64 %argv0, %argv1
    %resultPtr = alloca i64
    store i64 %result, i64* %resultPtr
    ret i64* %resultPtr
}
define i8* @or$Byte$Byte(i8* %arg0, i8* %arg1) {
    %argv0 = load i8, i8* %arg0
    %argv1 = load i8, i8* %arg1
    %result = or i8 %argv0, %argv1
    %resultPtr = alloca i8
    store i8 %result, i8* %resultPtr
    ret i8* %resultPtr
}
define i64* @xor$Long$Long(i64* %arg0, i64* %arg1) {
    %argv0 = load i64, i64* %arg0
    %argv1 = load i64, i64* %arg1
    %result = xor i64 %argv0, %argv1
    %resultPtr = alloca i64
    store i64 %result, i64* %resultPtr
    ret i64* %resultPtr
}
define i8* @xor$Byte$Byte(i8* %arg0, i8* %arg1) {
    %argv0 = load i8, i8* %arg0
    %argv1 = load i8, i8* %arg1
    %result = xor i8 %argv0, %argv1
    %resultPtr = alloca i8
    store i8 %result, i8* %resultPtr
    ret i8* %resultPtr
}
define i8* @byteAt$Pointer(i8** %pointer) {
    %result = load i8*, i8** %pointer
    ret i8* %result
}
define i64* @longAt$Pointer(i8** %pointer) {
    %ptr = bitcast i8** %pointer to i64**
    %result = load i64*, i64** %ptr
    ret i64* %result
}
define i8** @pointerAt$Pointer(i8** %pointer) {
    %ptr = bitcast i8** %pointer to i8***
    %result = load i8**, i8*** %ptr
    ret i8** %result
}
define i8** @pointerOf$Byte(i8* %byte) {
    %value = bitcast i8* %byte to i8*
    %result = alloca i8*
    store i8* %value, i8** %result
    ret i8** %result
}
define i8** @pointerOf$Long(i64* %long) {
    %value = bitcast i64* %long to i8*
    %result = alloca i8*
    store i8* %value, i8** %result
    ret i8** %result
}
define i8** @pointerOf$Pointer(i8** %pointer) {
    %value = bitcast i8** %pointer to i8*
    %result = alloca i8*
    store i8* %value, i8** %result
    ret i8** %result
}
define i8* @equals$Byte$Byte(i8* %arg0, i8* %arg1) {
    %result = call i8* @sub$Byte$Byte(i8* %arg0, i8* %arg1)
    ret i8* %result
}
define i8* @infiniteOnes() {
    %$tmp0 = alloca i8
    store i8 49, i8* %$tmp0
    %$tmp1 = call i8* @putChar$Byte(i8* %$tmp0)
    %$tmp2 = call i8* @infiniteOnes()
    ret i8* %$tmp2
}
define i64* @main() {
    %$tmp0 = alloca i8
    store i8 48, i8* %$tmp0
    %$tmp1 = call i8* @putChar$Byte(i8* %$tmp0)
    %$tmp2 = alloca i8
    store i8 47, i8* %$tmp2
    %$tmp3 = call i8* @putChar$Byte(i8* %$tmp2)
    %$tmp4 = alloca i8
    store i8 49, i8* %$tmp4
    %$tmp5 = call i8* @putChar$Byte(i8* %$tmp4)
    %$tmp6 = alloca i8
    store i8 58, i8* %$tmp6
    %$tmp7 = call i8* @putChar$Byte(i8* %$tmp6)
    %char = alloca i8
    %char$tmp8 = call i8* @getChar()
    %$tmp9 = alloca i8
    store i8 48, i8* %$tmp9
    %$tmp10 = call i8* @equals$Byte$Byte(i8* %char$tmp8, i8* %$tmp9)
    %$conditionv$tmp11 = load i8, i8* %$tmp10
    %$tmp12 = icmp eq i8 %$conditionv$tmp11, 0
    br i1 %$tmp12, label %$if$tmp13, label %$else$tmp15
$if$tmp13:
    %$tmp16 = alloca i8
    store i8 48, i8* %$tmp16
    %$tmp17 = call i8* @putChar$Byte(i8* %$tmp16)
    %$tmp18 = alloca i64
    store i64 0, i64* %$tmp18
    ret i64* %$tmp18
    br label %$endif$tmp14
$else$tmp15:
    %$tmp19 = alloca i8
    store i8 49, i8* %$tmp19
    %$tmp20 = call i8* @equals$Byte$Byte(i8* %char$tmp8, i8* %$tmp19)
    %$conditionv$tmp21 = load i8, i8* %$tmp20
    %$tmp22 = icmp eq i8 %$conditionv$tmp21, 0
    br i1 %$tmp22, label %$if$tmp23, label %$endif$tmp14
$if$tmp23:
    %$tmp24 = call i8* @infiniteOnes()
    br label %$endif$tmp14
$endif$tmp14:
    %$tmp25 = alloca i8
    store i8 105, i8* %$tmp25
    %$tmp26 = call i8* @putChar$Byte(i8* %$tmp25)
    %$tmp27 = alloca i8
    store i8 58, i8* %$tmp27
    %$tmp28 = call i8* @putChar$Byte(i8* %$tmp27)
    %$tmp29 = call i8* @putChar$Byte(i8* %char$tmp8)
    %$tmp30 = alloca i64
    store i64 1, i64* %$tmp30
    ret i64* %$tmp30
}
