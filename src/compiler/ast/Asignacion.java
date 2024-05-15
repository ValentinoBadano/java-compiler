/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class Asignacion extends Sentencia {
    
    private final Identificador identificador;
    private final ExpresionLogica expresion;

    public Asignacion(Identificador identificador, ExpresionLogica expresion) {
        this.identificador = identificador;
        this.expresion = expresion;
    }

    public Identificador getIdentificador() {
        return identificador;
    }

    public ExpresionLogica getExpresion() {
        return expresion;
    }

    
    @Override
    protected String getEtiqueta() {
        return ":=";
    }
    
      @Override
    protected String getId() {
        return "asignacion_" + this.hashCode();
    }
    
    @Override
    protected String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) +
                identificador.graficar(miId) +
                expresion.graficar(miId);
    }

}
