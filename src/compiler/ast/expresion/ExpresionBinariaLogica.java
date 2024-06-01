/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion;

/**
 *
 * @author Mari
 */
public abstract class ExpresionBinariaLogica extends ExpresionLogica {
    protected final ExpresionLogica izquierda;
    protected final ExpresionLogica derecha;

    public ExpresionBinariaLogica(ExpresionLogica izquierda, ExpresionLogica derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }


    @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getNombreOperacion());
    }

    protected abstract String getNombreOperacion();

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) +
                izquierda.graficar(miId) +
                derecha.graficar(miId);
    }

    @Override
    public String toString() {
        return izquierda + " " + this.getNombreOperacion() + " " + derecha;
    }
}
