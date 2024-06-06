/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.constante;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.llvm.CodeGeneratorHelper;
import compiler.simbolo.TablaSimbolos;

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
        return TablaSimbolos.ts.get(this.getValor().toString()).getLength() + 2;
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.STRING_LITERAL);
    }
    @Override
    public String generarCodigo() {
        // TODO saltos de lineas en strings
        StringBuilder resultado = new StringBuilder();
        this.setIr_ref(CodeGeneratorHelper.getNewGlobalPointer());
        resultado.append(String.format("%1$s = private constant [%3$s x i8] c\"%2$s\\0A\\00\"\n", this.getIr_ref(), this.getValor(), this.getLength()));
        return resultado.toString();
    }
    
}
