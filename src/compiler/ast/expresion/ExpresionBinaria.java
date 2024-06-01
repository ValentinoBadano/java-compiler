/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.casteo.EnteroAFloat;

/**
 *
 * @author Mari
 */
public abstract class ExpresionBinaria extends Expresion{
    protected ExpresionLogica izquierda;
    protected ExpresionLogica derecha;

    public ExpresionBinaria(ExpresionLogica izquierda, ExpresionLogica derecha) throws Exception {
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.validarTipo();
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

    @Override
    public TipoDato getTipo() {
        return tipo;
    }

    public void validarTipo() throws Exception {
        TipoPR tipoIzq = izquierda.getTipo().getOperador();
        TipoPR tipoDer = derecha.getTipo().getOperador();
        if (tipoDer == tipoIzq) {
            this.tipo = izquierda.getTipo();
        } else if (tipoIzq == TipoPR.PR_FLOAT && tipoDer == TipoPR.PR_INTEGER) {
            this.tipo = izquierda.getTipo();
            this.derecha = new EnteroAFloat(derecha); // castea derecha a float
        } else if (tipoIzq == TipoPR.PR_INTEGER && tipoDer == TipoPR.PR_FLOAT) {
            this.tipo = derecha.getTipo();
            this.izquierda = new EnteroAFloat(izquierda); // castea izquierda a float
        } else {
            throw new Exception(String.format("ERROR: Incompatibilidad de tipos en la operación %s %s %s%n", izquierda, getNombreOperacion(), derecha));
        }
    }
}
