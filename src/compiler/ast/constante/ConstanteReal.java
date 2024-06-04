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
public class ConstanteReal extends Constante {
    
    public ConstanteReal(Object valor) {
        super(valor);
    }

    @Override
    protected String getEtiqueta() {
        return String.format(String.format("float %s", getValor()));
    }
    
     @Override
    protected String getId() {
        return "cte_real_" + this.hashCode();
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.PR_FLOAT);
    }

    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        resultado.append(String.format("%1$s = fadd double 0.0, %2$s\n", this.getIr_ref(), this.getValor()));
        return resultado.toString();
    }
}
