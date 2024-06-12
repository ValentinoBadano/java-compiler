/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.constante;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.llvm.CodeGeneratorHelper;

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
        return String.format(String.format("bool %s", getValor()));
    }
    
    @Override
    protected String getId() {
        return "cte_boolean_" + this.hashCode();
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.PR_BOOLEAN);
    }
    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        // constante boolean -> i1 (1 bit)
        resultado.append(String.format("%1$s = add i1 0, %2$s\n", this.getIr_ref(), this.getBitValue()));
        return resultado.toString();
    }

    private String getBitValue() {
        if (this.getValor().equals("true")) {
            return "1";
        } else {
            return "0";
        }
    }
    
}
