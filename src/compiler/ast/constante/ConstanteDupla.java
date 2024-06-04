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
public class ConstanteDupla extends Constante{

    public ConstanteDupla(Object valor) {
        super(valor);
    }

    @Override
    protected String getEtiqueta() {
        return String.format(String.format("duple %s", getValor()));
    }
    
    @Override
    protected String getId() {
        return "cte_dupla_" + this.hashCode();
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.PR_DUPLE);
    }
    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        // TODO tipo de dato tupla
        resultado.append(String.format("%1$s = add i32 0, %2$s\n", this.getIr_ref(), this.getValor()));
        return resultado.toString();
    }
}
