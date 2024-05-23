/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class SentenciaUnless extends Sentencia{
    private ExpresionLogica expresion;
    private SentenciaUnlessThen sentenciasThen;
    private SentenciaUnlessElse sentenciasElse;
    
    
    public SentenciaUnless(ExpresionLogica expresion, SentenciaUnlessThen sentenciasThen) {
        this.expresion = expresion;
        this.sentenciasThen = sentenciasThen;
    }
    
    public SentenciaUnless(ExpresionLogica expresion, SentenciaUnlessThen sentenciasThen, SentenciaUnlessElse sentenciasElse ) {
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

    public SentenciaUnlessThen getSentenciasThen() {
        return sentenciasThen;
    }

    public void setSentenciasThen(SentenciaUnlessThen sentenciasThen) {
        this.sentenciasThen = sentenciasThen;
    }

    public SentenciaUnlessElse getSentenciasElse() {
        return sentenciasElse;
    }

    public void setSentenciasElse(SentenciaUnlessElse sentenciasElse) {
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
                  graf += sentenciasThen.graficar(miId);
           
        }
        if(sentenciasElse != null){
               graf += sentenciasElse.graficar(miId);
           
        }
        
        return super.graficar(idPadre) + getExpresion().graficar(miId)  + graf;
    }

    public String toString() {
        return "unless " + expresion.toString() + " then " + sentenciasThen.toString() + " else " + sentenciasElse.toString() + " end";
    }
    
    
    
    
    
}
