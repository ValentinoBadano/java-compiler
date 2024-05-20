/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public abstract class  ExpresionUnariaLogica extends ExpresionLogica {
    
    protected final ExpresionLogica derecha;

    public ExpresionUnariaLogica (ExpresionLogica derecha) {
        this.derecha = derecha;
    }


    @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getNombreOperacion());
    }

    protected abstract String getNombreOperacion();

    @Override
    protected String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) +
                derecha.graficar(miId);
    }

    @Override
    public String toString() {
        return this.getNombreOperacion() + " " + derecha;
    }
}
