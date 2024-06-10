/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion.aritmetica;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.expresion.ExpresionBinaria;
import compiler.ast.expresion.ExpresionLogica;

/**
 *
 * @author Mari
 */
public class Division extends ExpresionBinaria {

    public Division(ExpresionLogica izquierda, ExpresionLogica derecha) throws Exception {
        super(izquierda, derecha);
    }

    @Override
    public String get_llvm_op_code() {
        if (this.getTipo().getOperador() == TipoPR.PR_FLOAT || this.getTipo().getOperador() == TipoPR.PR_DUPLE) {
            return "fdiv";
        } else {
            return "sdiv";
        }
    }

    @Override
    protected String getNombreOperacion() {
        return "/";
    }
    
    @Override
    protected String getId() {
        return "division_" + this.hashCode();
    }

    // TODO dividir por 0

    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        resultado.append(super.generarCodigo());
        return resultado.toString();
    }
}
