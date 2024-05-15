/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class ExpresionNOT extends ExpresionUnariaLogica{
    
    public ExpresionNOT(ExpresionLogica derecha) {
        super(derecha);
    }
    
    @Override
     protected String getNombreOperacion() {
            return "not";
          
    }
     
    @Override
    protected String getId() {
        return "exp_not_" + this.hashCode();
    }
}
