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
public class SentenciaContinue extends Sentencia {
    
     public TipoPR operador;

    public SentenciaContinue(TipoPR operador) {
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
        return "continue_" + this.hashCode();
    }

    public String generarCodigo() {
        // TODO consultar break y continue
        StringBuilder resultado = new StringBuilder();
        if (!CodeGeneratorHelper.isRepeat()) {
            throw new RuntimeException("Continue fuera de un repeat");
        }
        String start = CodeGeneratorHelper.repeatStartFlags.peek();
        resultado.append("br label %" + start + "\n");
        return resultado.toString();
    }
    
}
