/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

import compiler.ast.expresion.ExpresionBinariaLogica;
import compiler.ast.expresion.ExpresionLogica;

/**
 *
 * @author Mari
 */
public class MayorIgual extends ExpresionBinariaLogica {

    public MayorIgual (ExpresionLogica izquierda, ExpresionLogica derecha) {
        super(izquierda, derecha);
    }
    
    @Override
    protected String getNombreOperacion() {
        return ">=";
    }
    
    @Override
    protected String getId() {
        return "mayor_igual_" + this.hashCode();
    }
}