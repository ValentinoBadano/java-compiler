/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.sentencia;

import compiler.ast.TipoPR;
import compiler.llvm.CodeGeneratorHelper;

/**
 *
 * @author Mari
 */
public class SentenciaBreak extends Sentencia{
    public TipoPR operador;

    public SentenciaBreak(TipoPR operador) {
        this.operador = operador;
    }

    public TipoPR getOperador() {
        return operador;
    }

    public void setOperador(TipoPR operador) {
        this.operador = operador;
    }
    
     @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getOperador().toString());
    }
    
    @Override
    public String graficar(String idPadre) {
        return super.graficar(idPadre);
        
    }
    
     @Override
    protected String getId() {
        return "break_" + this.hashCode();
    }

    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        if (!CodeGeneratorHelper.isRepeat()) {
            throw new RuntimeException("Break fuera de un repeat");
        }

        String end = CodeGeneratorHelper.repeatEndFlags.peek();
        resultado.append("br label %" + end + "\n");
        return resultado.toString();
    }
}
