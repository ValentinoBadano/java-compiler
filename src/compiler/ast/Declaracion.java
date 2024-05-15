/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Mari
 */
public class Declaracion extends Expresion {
    
    private TipoDato tipoDato;
    private List<String> listaIdentificador;
  

    public Declaracion(TipoDato tipoDato, List<String> listaIdentificador) {
        this.tipoDato = tipoDato;
        this.listaIdentificador = listaIdentificador;
    }

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(TipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }

    public List<String> getListaIdentificador() {
        return listaIdentificador;
    }

    public void setListaIdentificador(List<String> listaIdentificador) {
        this.listaIdentificador = listaIdentificador;
    }
    
    @Override
    protected String getId() {
          return "declaracion_" + this.hashCode();
    }
    
    @Override
    protected String getEtiqueta() {
        return ":";
    }
    
    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        TipoDato td = new TipoDato(getTipoDato().getOperador());
        String graficoLista = super.graficar(idPadre) + td.graficar(miId);
        
        
        if (listaIdentificador.size() > 1) {
            for (int i = 0; i < listaIdentificador.size(); i++) {
                Identificador identificador = new Identificador(listaIdentificador.get(i));
                
               
               
               graficoLista +=  new Identificador(listaIdentificador.get(i)).graficar(miId);
              
            }  
        }
         return graficoLista;
    }
    
}
