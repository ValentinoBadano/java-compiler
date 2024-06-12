;Programa: Prueba
source_filename = "Prueba.txt"
target datalayout = "e-m:w-p270:32:32-p271:32:32-p272:64:64-i64:64-i128:128-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-windows-msvc19.33.0"

declare i32 @printf(i8*, ...)

	@.int = private constant [4 x i8] c"%d\0A\00"
	@.float = private constant [4 x i8] c"%f\0A\00"
	@.boolean = private constant [4 x i8] c"%d\0A\00"
	@.duple = private constant [10 x i8] c"(%f, %f)\0A\00"
%struct.Tuple = type { double, double }
declare i32 @scanf(i8* %0, ...) 

@int_read_format = unnamed_addr constant [3 x i8] c"%d\00"
@double_read_format = unnamed_addr constant [4 x i8] c"%lf\00"
@bool_read_format = unnamed_addr constant [3 x i8] c"%d\00"

define i32 @main(i32, i8**) {
		%ptro.1 = alloca %struct.Tuple
	%ptro.2 = alloca %struct.Tuple
%a = alloca %struct.Tuple
	
	%ptro.1.1 = getelementptr %struct.Tuple, %struct.Tuple* %ptro.1, i32 0, i32 0
	%ptro.1.2 = getelementptr %struct.Tuple, %struct.Tuple* %ptro.1, i32 0, i32 1
	store double 1.5, double* %ptro.1.1
	store double 2.5, double* %ptro.1.2
	
	%ptro.2.1 = getelementptr %struct.Tuple, %struct.Tuple* %ptro.2, i32 0, i32 0
	%ptro.2.2 = getelementptr %struct.Tuple, %struct.Tuple* %ptro.2, i32 0, i32 1
	store double 1.5, double* %ptro.2.1
	store double 2.5, double* %ptro.2.2
	
	%ptro.5 = load double, double* %ptro.1.1
	%ptro.6 = load double, double* %ptro.1.2
	%ptro.7 = load double, double* %ptro.2.1
	%ptro.8 = load double, double* %ptro.2.2
	%ptro.9 = fadd double %ptro.5, %ptro.7
	%ptro.10 = fadd double %ptro.6, %ptro.8
	store double %ptro.9, double* %ptro.4.1
	store double %ptro.10, double* %ptro.4.2
	%ptro.11 = load double, double* %ptro.4.1
	%ptro.12 = load double, double* %ptro.4.2
	%ptro.13 = getelementptr %struct.Tuple, %struct.Tuple* %a, i32 0, i32 0
	%ptro.14 = getelementptr %struct.Tuple, %struct.Tuple* %a, i32 0, i32 1
	store double %ptro.11, double* %ptro.13
	store double %ptro.12, double* %ptro.14
	%ptro.15 = load %struct.Tuple, %struct.Tuple* %a
	%ptro.16 = call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @.duple, i32 0, i32 0), %struct.Tuple %ptro.15)
	ret i32 0
}


