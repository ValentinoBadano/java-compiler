/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion;

import compiler.ast.Identificador;
import compiler.ast.TipoDato;
import compiler.ast.expresion.Expresion;

import java.util.List;

/**
 *
 * @author Mari
 */
public class Declaracion extends Expresion {
    
    private TipoDato tipoDato;
    private List<Identificador> listaIdentificador;
  

    public Declaracion(TipoDato tipoDato, List<Identificador> listaIdentificador) {
        this.tipoDato = tipoDato;
        this.listaIdentificador = listaIdentificador;
    }

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(TipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }

    public List<Identificador> getListaIdentificador() {
        return listaIdentificador;
    }

    public void setListaIdentificador(List<Identificador> listaIdentificador) {
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
    public String toString() {
        return tipoDato + ": " + listaIdentificador.toString();
    }

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        TipoDato td = new TipoDato(getTipoDato().getOperador());
        String graficoLista = super.graficar(idPadre) + td.graficar(miId);
        
        
        if (listaIdentificador.size() >= 1) {
            for (Identificador identificador: listaIdentificador) {
             graficoLista +=  identificador.graficar(miId);
              
            }  
        }
         return graficoLista;
    }
    
}
