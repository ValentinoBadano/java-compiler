/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.constante;

import compiler.llvm.CodeGeneratorHelper;

/**
 *
 * @author Mari
 */
public class ConstanteString extends Constante{
    
     public ConstanteString(Object valor) {
        super(valor);
    }

    @Override
    protected String getEtiqueta() {
        return String.format(String.format("String %s", getValor()));
    }
    
    @Override
    protected String getId() {
        return "cte_string_" + this.hashCode();
    }

    public int getLength() {
         return this.getValor().toString().length() + 1;
    }
    @Override
    public String generarCodigo() {
         // TODO: arreglar
        StringBuilder resultado = new StringBuilder();
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        resultado.append(String.format("%1$s = private constant [%3$s x i8] c\"%2$s\\00\"\n", this.getIr_ref(), this.getValor(), getLength()));
        return resultado.toString();
    }
    
}
