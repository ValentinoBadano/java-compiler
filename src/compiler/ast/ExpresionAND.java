/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class ExpresionAND extends ExpresionBinariaLogica{

    public ExpresionAND(ExpresionLogica izquierda, ExpresionLogica derecha) {
        super(izquierda, derecha);
    }

    @Override
    protected String getNombreOperacion() {
        return "and";
    }
    
    @Override
    protected String getId() {
        return "exp_and_" + this.hashCode();
    }
}
