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
public class Mayor extends Comparacion {

    public Mayor(ExpresionLogica izquierda, ExpresionLogica derecha) throws Exception {
        super(izquierda, derecha);
    }


    @Override
    public String get_llvm_op_code(TipoPR tipo) {
        if (tipo == TipoPR.PR_FLOAT) {
            return "fcmp ogt";
        }
        return "icmp sgt";
    }

    @Override
    protected String getNombreOperacion() {
        return ">";
    }
    
    @Override
    protected String getId() {
        return "mayor_" + this.hashCode();
    }
}
