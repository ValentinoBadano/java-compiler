;Programa: Prueba
source_filename = "Prueba.txt"
target datalayout = "e-m:w-p270:32:32-p271:32:32-p272:64:64-i64:64-i128:128-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-windows-msvc19.33.0"

declare i32 @printf(i8*, ...)

@.integer = private constant [4 x i8] c"%d\0A\00"

define i32 @main(i32, i8**) {
	%a = alloca i32
	%b = alloca i32
	%c = alloca i32
	
	%ptro.1 = add i32 0, 2
	
	store i32 %ptro.1, i32* %a%ptro.2 = add i32 0, 3
	
	store i32 %ptro.2, i32* %b
	%ptro.3 = load i32, i32* %a
	%ptro.4 = load i32, i32* %b%ptro.5 = add i32 %ptro.3, %ptro.4
	store i32 %ptro.5, i32* %c
	ret i32 0
}


