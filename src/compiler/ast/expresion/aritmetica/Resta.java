/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion.aritmetica;

import compiler.ast.TipoPR;
import compiler.ast.expresion.ExpresionBinaria;
import compiler.ast.expresion.ExpresionLogica;

/**
 *
 * @author Mari
 */
public class Resta extends ExpresionBinaria {

    public Resta(ExpresionLogica izquierda, ExpresionLogica derecha) throws Exception {
        super(izquierda, derecha);
    }
    
    @Override
    protected String getNombreOperacion() {
        return "-";
    }
    
     @Override
    protected String getId() {
        return "resta_" + this.hashCode();
    }

    @Override
    public String get_llvm_op_code() {
        if (this.getTipo().getOperador() == TipoPR.PR_FLOAT || this.getTipo().getOperador() == TipoPR.PR_DUPLE) {
            return "fsub";
        } else {
            return "sub";
        }
    }
}
