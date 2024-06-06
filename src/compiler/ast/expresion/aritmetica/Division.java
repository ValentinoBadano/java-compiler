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
        return "sdiv";
    }

    @Override
    protected String getNombreOperacion() {
        return "/";
    }
    
    @Override
    protected String getId() {
        return "division_" + this.hashCode();
    }

    // caso especial: la división siempre devuelve un entero
    // TODO dividir por 0
    // TODO division entre flotantes
    @Override
    public TipoDato getTipo() {
        return new TipoDato(TipoPR.PR_INTEGER);
    }
}