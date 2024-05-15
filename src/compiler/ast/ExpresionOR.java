/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class ExpresionOR extends ExpresionBinariaLogica {

    public ExpresionOR(ExpresionLogica izquierda, ExpresionLogica derecha) {
        super(izquierda, derecha);
    }
    
    
    @Override
    protected String getNombreOperacion() {
        return "or";
    }
   
    @Override
    protected String getId() {
        return "exp_or_" + this.hashCode();
    }
    
}
