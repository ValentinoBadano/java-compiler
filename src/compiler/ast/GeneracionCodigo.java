/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

import compiler.ast.expresion.Declaracion;
import compiler.ast.sentencia.Sentencia;
import compiler.llvm.CodeGeneratorHelper;

/**
 *
 * @author Mari
 */
public class GeneracionCodigo extends Nodo{
    
    private final Programa programa;
    
    public GeneracionCodigo(Programa programa){
        this.programa = programa;
    }
    
    
    public String graficar() {
        
        // Acá se dispara la invocación a los métodos graficar() de los nodos.
        // Como la GeneracionCodigo no tiene padre, se inicia pasando null.  
        StringBuilder resultado = new StringBuilder();
        resultado.append("graph G {");
        // acomodar estos graficar
        resultado.append(this.programa.graficar(null));
        resultado.append(this.programa.graficar());

        resultado.append("}");
        return resultado.toString();
    }

    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        resultado.append(";Programa: Prueba\n");
        resultado.append("source_filename = \"Prueba.txt\"\n");
        resultado.append("target datalayout = \"e-m:w-p270:32:32-p271:32:32-p272:64:64-i64:64-i128:128-f80:128-n8:16:32:64-S128\"\n");
        resultado.append("target triple = \"x86_64-pc-windows-msvc19.33.0\"\n\n");
        resultado.append("declare i32 @printf(i8*, ...)\n");
        resultado.append("\n");
        resultado.append("@.integer = private constant [4 x i8] c\"%d\\0A\\00\"\n");
        resultado.append("\n");
        resultado.append("define i32 @main(i32, i8**) {\n\t");

        StringBuilder resultado_programa = new StringBuilder();

        for (Declaracion d: programa.declaraciones) {
            resultado_programa.append(d.generarCodigo());
        }

        resultado_programa.append("\n");

        for (Sentencia s: programa.sentencias) {
            resultado_programa.append(s.generarCodigo());
        }

        resultado.append(resultado_programa.toString().replaceAll("\n", "\n\t"));

        resultado.append("\n\tret i32 0\n");
        resultado.append("}\n\n");


        return resultado.toString();
    }
    
}
