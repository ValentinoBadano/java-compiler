/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

import compiler.ast.expresion.Declaracion;
import compiler.ast.sentencia.Sentencia;
import compiler.simbolo.Simbolo;

import java.util.*;

/**
 *
 * @author Mari
 */
public class Programa extends Nodo {

    List<Declaracion> declaraciones = new ArrayList<>();
    List<Sentencia> sentencias = new ArrayList<>();
    public static final List<Declaracion> declaracionesFibo = new ArrayList<>();

    public Programa( List<Declaracion> declaraciones, List<Sentencia> sentencias) {

        this.declaraciones = declaraciones;
        this.sentencias = sentencias;
        if (!declaracionesFibo.isEmpty()) {
            declaraciones.addAll(declaracionesFibo);
            declaracionesFibo.clear();
        }
    }

    public String graficar() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("graph G {");
        resultado.append(this.graficar(null));
        if (declaraciones != null) {
            for(Declaracion nodoDeclaracion : declaraciones){
                resultado.append(nodoDeclaracion.graficar(getId()));
            }
        }

        if (sentencias != null) {
            for (Sentencia nodoSentencia : sentencias) {
                resultado.append(nodoSentencia.graficar(getId()));
            }
        }

        resultado.append("}");
        return resultado.toString();
    }

    @Override
    protected String getEtiqueta() {
        return "Programa";
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

        resultado.append("\t@.int = private constant [4 x i8] c\"%d\\0A\\00\"\n"); // variable para imprimir enteros
        resultado.append("\t@.float = private constant [4 x i8] c\"%f\\0A\\00\"\n"); // variable para imprimir flotantes
        resultado.append("\t@.boolean = private constant [4 x i8] c\"%d\\0A\\00\"\n"); // variable para imprimir booleanos
        resultado.append("\t@.duple = private constant [10 x i8] c\"(%f, %f)\\0A\\00\"\n"); // variable para imprimir duplas


        // declara variables string globales aquí
        Iterator<String> iterator = sentenciasPrograma.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.startsWith("@")) {
                resultado.append("\t").append(s).append("\n");
                iterator.remove();
            }
        }

        resultado.append("%struct.Tuple = type { double, double }\n");
        resultado.append("declare i32 @scanf(i8* %0, ...) \n\n");
        resultado.append("@int_read_format = unnamed_addr constant [3 x i8] c\"%d\\00\"\n");
        resultado.append("@double_read_format = unnamed_addr constant [4 x i8] c\"%lf\\00\"\n");
        resultado.append("@bool_read_format = unnamed_addr constant [3 x i8] c\"%d\\00\"\n");

        // --- sección de programa ---
        resultado.append("\ndefine i32 @main(i32, i8**) {\n\t");

        Iterator<String> iterator2 = sentenciasPrograma.iterator();
        while (iterator2.hasNext()) {
            String s = iterator2.next();
            if (s.contains("alloca")) {
                resultado.append("\t").append(s).append("\n");
                iterator2.remove();
            }
        }

        StringBuilder resultado_programa = new StringBuilder();

        try {
            // si no hay declaraciones el foreach lanza una NullPointerException y esto no debería detener la compilación
            for (Declaracion d : declaraciones) {
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
            for (Sentencia s: sentencias) {
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
