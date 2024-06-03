/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

import compiler.ast.expresion.Declaracion;
import compiler.ast.sentencia.Sentencia;
import compiler.ast.sentencia.SentenciaShow;
import compiler.llvm.CodeGeneratorHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        List<String> sentenciasPrograma = getCodigoPrograma();
        StringBuilder resultado = new StringBuilder();
        resultado.append(";Programa: Prueba\n");
        resultado.append("source_filename = \"Prueba.txt\"\n");
        resultado.append("target datalayout = \"e-m:w-p270:32:32-p271:32:32-p272:64:64-i64:64-i128:128-f80:128-n8:16:32:64-S128\"\n");
        resultado.append("target triple = \"x86_64-pc-windows-msvc19.33.0\"\n\n");

        // --- sección de declaración ---
        resultado.append("declare i32 @printf(i8*, ...)\n\n");
        resultado.append("\t@.integer = private constant [4 x i8] c\"%d\\0A\\00\"\n"); // variable para imprimir enteros
        // declara variables string globales aquí
        Iterator<String> iterator = sentenciasPrograma.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.startsWith("@")) {
                resultado.append("\t").append(s).append("\n");
                iterator.remove();
            }
        }

        // --- sección de programa ---
        resultado.append("\ndefine i32 @main(i32, i8**) {\n\t");

        StringBuilder resultado_programa = new StringBuilder();

        try {
            // si no hay declaraciones el foreach lanza una NullPointerException y esto no debería detener la compilación
            for (Declaracion d : programa.declaraciones) {
                resultado_programa.append(d.generarCodigo());
                resultado_programa.append("\n");
            }
        } catch (NullPointerException ignored) {
            System.out.println("Declaraciones vacías");
        }

        for (String s: sentenciasPrograma) {
            resultado_programa.append(s);
            resultado_programa.append("\n");
        }


        resultado.append(resultado_programa.toString().replaceAll("\n", "\n\t"));

        resultado.append("ret i32 0\n");
        resultado.append("}\n\n");

        return resultado.toString();
    }

    private List<String> getCodigoPrograma() {
        // retorna una lista con las sentencias del programa separadas por \n
        StringBuilder sb = new StringBuilder();
        try {
            for (Sentencia s: programa.sentencias) {
                sb.append(s.generarCodigo());
            }
        } catch (NullPointerException ignored) {
            System.out.println("Programa vacío");
        }

        String codigo = sb.toString();

        String[] lineasArray = codigo.split("\n");
        List<String> lineasList = new ArrayList<>();

        for (String linea : lineasArray) {
            lineasList.add(linea);
        }

        return lineasList;
    }

}
