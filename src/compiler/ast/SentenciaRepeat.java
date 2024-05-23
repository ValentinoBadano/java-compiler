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
public class SentenciaRepeat extends Sentencia{
    
     private List<Sentencia> sentenciasRepeat;
     private SentenciaUntil until;

    public SentenciaRepeat(List<Sentencia> sentenciasRepeat, SentenciaUntil until) {
        this.sentenciasRepeat = sentenciasRepeat;
        this.until = until;
    }

   



    public List<Sentencia> getSentenciasRepeat() {
        return sentenciasRepeat;
    }

    public void setSentenciasRepeat(List<Sentencia> sentenciasRepeat) {
        this.sentenciasRepeat = sentenciasRepeat;
    }
    
     protected String getNombreOperacion() {
        return "repeat";
    }
    
    @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getNombreOperacion());
    }
    
    @Override
     protected String getId() {
          return "repeat_" + this.hashCode();
    }
    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        
        String graf = "";
        if(sentenciasRepeat != null){
              for(Sentencia sentenciaRepeat : sentenciasRepeat){
                  graf += sentenciaRepeat.graficar(miId);
            }
        }
        
        return super.graficar(idPadre) + graf + until.graficar(miId);
    }

    @Override
    public String toString() {
        return "repeat " + sentenciasRepeat.toString() + until.toString();
    }
}
