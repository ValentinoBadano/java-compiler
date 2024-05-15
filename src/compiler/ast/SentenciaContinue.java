/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

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
    protected String graficar(String idPadre) {
        return super.graficar(idPadre);
        
    }
    
     @Override
    protected String getId() {
        return "continue_" + this.hashCode();
    } 
    
}
