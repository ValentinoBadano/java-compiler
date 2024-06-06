/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.sentencia;

import compiler.ast.expresion.ExpresionLogica;

/**
 *
 * @author Mari
 */
public class SentenciaUntil extends Sentencia{
    public ExpresionLogica expresion;

    public SentenciaUntil(ExpresionLogica expresion) {
 
        this.expresion = expresion;
    }

    
    public ExpresionLogica getExpresion() {
        return expresion;
    }

    public void setExpresion(ExpresionLogica expresion) {
        this.expresion = expresion;
    }
    
    @Override
    protected String getEtiqueta() {
        return "until";
    }
    
    @Override
    public String graficar(String idPadre) {
         final String miId = this.getId();     
        return super.graficar(idPadre) + getExpresion().graficar(miId);
        
    }

    @Override
    public String toString() {
        return " until " + this.expresion.toString();
    }

    @Override
    protected String getId() {
        return "until_" + this.hashCode();
    }

    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        resultado.append(expresion.generarCodigo());
        return resultado.toString();
    }
    
}
