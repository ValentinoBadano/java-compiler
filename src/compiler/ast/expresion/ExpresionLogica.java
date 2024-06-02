/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion;

import compiler.ast.Nodo;
import compiler.ast.TipoDato;
import compiler.ast.TipoPR;

/**
 *
 * @author Mari
 */
public abstract class ExpresionLogica extends Nodo {
    protected TipoDato tipo;
    private String ir_ref;

    public ExpresionLogica() {
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.PR_BOOLEAN);
    }

    public void setTipo(TipoDato tipo) {
        this.tipo = tipo;
    }


    public String getIr_ref() {
        return ir_ref;
    }

    public void setIr_ref(String ir_ref) {
        this.ir_ref = ir_ref;
    }
}
