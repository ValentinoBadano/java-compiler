/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

import java.util.List;

/**
 *
 * @author Mari
 */
public class SentenciaUnlessElse extends Sentencia {
       private List<Sentencia> sentenciasElse;

    public SentenciaUnlessElse(List<Sentencia> sentenciasElse) {
        this.sentenciasElse = sentenciasElse;
    }

    public List<Sentencia> getSentenciasElse() {
        return sentenciasElse;
    }

    public void setSentenciasElse(List<Sentencia> sentenciasElse) {
        this.sentenciasElse = sentenciasElse;
    }

       
    protected String getNombreOperacion() {
        return "else";
    }
    
    @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getNombreOperacion());
    }
    
    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        
        String graf = "";
        if(sentenciasElse != null){
              for(Sentencia sentenciaWhen : sentenciasElse){
                  graf += sentenciaWhen.graficar(miId);
            }
        }
        
        return super.graficar(idPadre) + graf;
    }

    public String toString() {
        return this.sentenciasElse.toString();
    }
}
