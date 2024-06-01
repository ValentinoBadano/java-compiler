/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion;

import compiler.ast.TipoDato;
import compiler.ast.expresion.ExpresionLogica;

/**
 *
 * @author Mari
 */
public abstract class Expresion extends ExpresionLogica
{
    protected TipoDato tipo;

    public Expresion() {
    }
    
    
}
