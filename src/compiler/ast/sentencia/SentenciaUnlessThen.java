/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.sentencia;

import java.util.List;

/**
 *
 * @author Mari
 */
public class SentenciaUnlessThen extends Sentencia {
       private List<Sentencia> sentenciasThen;
       private String tag;

    public void setSentenciasThen(List<Sentencia> sentenciasThen) {
        this.sentenciasThen = sentenciasThen;
    }

    public List<Sentencia> getSentenciasThen() {
        return sentenciasThen;
    }

    public SentenciaUnlessThen(List<Sentencia> sentenciasThen) {
        this.sentenciasThen = sentenciasThen;
    }
    
      protected String getNombreOperacion() {
        return "then";
    }
    
    @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getNombreOperacion());
    }
    
    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        
        String graf = "";
        if(sentenciasThen != null){
              for(Sentencia sentenciaWhen : sentenciasThen){
                  graf += sentenciaWhen.graficar(miId);
            }
        }
        
        return super.graficar(idPadre) + graf;
    }

    public String toString() {
        return this.sentenciasThen.toString();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        for(Sentencia sentencia : sentenciasThen){
            resultado.append(sentencia.generarCodigo());
        }
        return resultado.toString();
    }
}
