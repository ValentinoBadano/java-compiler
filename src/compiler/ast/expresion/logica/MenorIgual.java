/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion.logica;

import compiler.ast.TipoPR;
import compiler.ast.expresion.ExpresionLogica;

/**
 *
 * @author Mari
 */
public class MenorIgual extends Comparacion {

    public MenorIgual(ExpresionLogica izquierda, ExpresionLogica derecha) throws Exception {
        super(izquierda, derecha);
    }


    public String get_llvm_op_code(TipoPR tipo) {
        if (tipo == TipoPR.PR_FLOAT) {
            return "fcmp ole";
        }
        return "icmp sle";
    }
    
    @Override
    protected String getNombreOperacion() {
        return "<=";
    }
    
    @Override
    protected String getId() {
        return "menor_igual_" + this.hashCode();
    }
}