/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

import compiler.ast.expresion.Declaracion;
import compiler.ast.sentencia.Sentencia;
import compiler.simbolo.Simbolo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mari
 */
public class Programa extends Nodo {

    List<Declaracion> declaraciones = new ArrayList<>();
    List<Sentencia> sentencias = new ArrayList<>();
    public static List<Declaracion> declaracionesFibo = new ArrayList<>();

    public static Map<String, Simbolo> tablaDeSimbolos = new HashMap<>();


    public Programa( List<Declaracion> declaraciones, List<Sentencia> sentencias) {

        this.declaraciones = declaraciones;
        this.sentencias = sentencias;
        if (!declaracionesFibo.isEmpty()) {
            declaraciones.addAll(declaracionesFibo);
            declaracionesFibo.clear();
        }
    }
 
    public Programa() {

    }

    public static Map<String, Simbolo> getTablaSimbolos() {
        return tablaDeSimbolos;
    }

    public static void setTablaSimbolos(Map<String, Simbolo> tablaSimbolos) {
        tablaDeSimbolos = tablaSimbolos;
    }

    public List<Declaracion> getDeclaraciones() {
        return declaraciones;
    }

    public void setDeclaraciones(List<Declaracion> declaraciones) {
        this.declaraciones = declaraciones;
    }
   

    public List<Sentencia> getSentencias() {
        return sentencias;
    }

    public void setSentencias(List<Sentencia> sentencias) {
        if(sentencias != null)
            this.sentencias = sentencias;
    }
    
    
    public String graficar() {
        StringBuilder resultado = new StringBuilder();

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

        return resultado.toString();
    }

    
}
