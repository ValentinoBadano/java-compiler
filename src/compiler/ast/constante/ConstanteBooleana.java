/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.constante;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;

/**
 *
 * @author Mari
 */
public class ConstanteBooleana extends Constante {

    public ConstanteBooleana(Object valor) {
        super(valor);
    }
    
    @Override
    protected String getEtiqueta() {
        return String.format(String.format("Booleano %s", getValor()));
    }
    
    @Override
    protected String getId() {
        return "cte_boolean_" + this.hashCode();
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.PR_BOOLEAN);
    }
    
}
