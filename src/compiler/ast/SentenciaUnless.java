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
public class SentenciaUnless extends Sentencia{
    private ExpresionLogica expresion;
    private List<Sentencia> sentenciasThen;
    private List<Sentencia> sentenciasElse;
    
    
    public SentenciaUnless(ExpresionLogica expresion, List<Sentencia> sentenciasThen) {
        this.expresion = expresion;
        this.sentenciasThen = sentenciasThen;
    }
    
    public SentenciaUnless(ExpresionLogica expresion, List<Sentencia> sentenciasThen, List<Sentencia> sentenciasElse ) {
        this.expresion = expresion;
        this.sentenciasThen = sentenciasThen;
        this.sentenciasElse = sentenciasElse;
    }

    public ExpresionLogica getExpresion() {
        return expresion;
    }

    public void setExpresion(ExpresionLogica expresion) {
        this.expresion = expresion;
    }

    public List<Sentencia> getSentenciasThen() {
        return sentenciasThen;
    }

    public void setSentenciasThen(List<Sentencia> sentenciasThen) {
        this.sentenciasThen = sentenciasThen;
    }

    public List<Sentencia> getSentenciasElse() {
        return sentenciasElse;
    }

    public void setSentenciasElse(List<Sentencia> sentenciasElse) {
        this.sentenciasElse = sentenciasElse;
    }
    
    protected String getNombreOperacion() {
        return "unless";
    }
    
    @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getNombreOperacion());
    }
    
    @Override
     protected String getId() {
          return "unless_" + this.hashCode();
    }
     
     @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        
        String graf = "";
        if(sentenciasThen != null){
              for(Sentencia sentenciaThen: sentenciasThen){
                  graf += sentenciaThen.graficar(miId);
            }
        }
        if(sentenciasElse != null){
              for(Sentencia sentenciaElse : sentenciasElse){
                  graf += sentenciaElse.graficar(miId);
            }
        }
        
        return super.graficar(idPadre) + getExpresion().graficar(miId)  + graf;
    }
    
    
    
}
