/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion.logica;

import compiler.ast.TipoPR;
import compiler.ast.expresion.ExpresionBinariaLogica;
import compiler.ast.expresion.ExpresionLogica;

/**
 *
 * @author Mari
 */
public class ExpresionOR extends ExpresionBinariaLogica {

    public ExpresionOR(ExpresionLogica izquierda, ExpresionLogica derecha) throws Exception {
        super(izquierda, derecha);
    }

    public String get_llvm_op_code(TipoPR tipo) {
        return "or";
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
