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
        String str = this.getValor().toString();
        int length = str.length();
        return length + 1;
    }

    public String getProcessedValue() {
        String str = this.getValor().toString();
        StringBuilder processed = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\n') {
                processed.append("\\0A");
            } else if (str.charAt(i) == '\"') {
                processed.append("\\22");
            } else if (str.charAt(i) == '\t') {
                processed.append("\\09");
            } else {
                processed.append(str.charAt(i));
            }
        }
        return processed.toString();
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.STRING_LITERAL);
    }
    @Override
    public String generarCodigo() {
        // TODO saltos de lineas en strings y escapar comillas
        StringBuilder resultado = new StringBuilder();
        this.setIr_ref(CodeGeneratorHelper.getNewGlobalPointer());
        resultado.append(String.format("%1$s = private constant [%3$s x i8] c\"%2$s\\00\"\n", this.getIr_ref(), this.getProcessedValue(), this.getLength()));
        return resultado.toString();
    }
    
}
