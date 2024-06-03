;Programa: Prueba
source_filename = "Prueba.txt"
target datalayout = "e-m:w-p270:32:32-p271:32:32-p272:64:64-i64:64-i128:128-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-windows-msvc19.33.0"

declare i32 @printf(i8*, ...)

@.integer = private constant [4 x i8] c"%d\0A\00"

define i32 @main(i32, i8**) {
	%a = alloca i32
	%b = alloca i32
	
	%ptro.1 = add i32 0, 30
	
	%ptro.2 = add i32 0, 4
	
	%ptro.3 = add i32 %ptro.1, %ptro.2
	
	store i32 %ptro.3, i32* %a
	
	%ptro.4 = load i32, i32* %a
	%ptro.5 = add i32 0, 35
	
	%ptro.6 = add i32 %ptro.4, %ptro.5
	
	store i32 %ptro.6, i32* %b
	
	%ptro.7 = load i32, i32* %b%ptro.8 = call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @.integer, i32 0, i32 0), i32 %ptro.7)
	
	ret i32 0
}


