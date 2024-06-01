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

    public ExpresionBinaria(ExpresionLogica izquierda, ExpresionLogica derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.getTipo();
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

    public TipoDato getTipo() {
        if (izquierda.getTipo().getOperador() == derecha.getTipo().getOperador()) {
            this.tipo = izquierda.getTipo();
        } else if (izquierda.getTipo().getOperador() == TipoPR.PR_FLOAT) {
            this.tipo = izquierda.getTipo();
            // castear derecha a float
            this.derecha = new EnteroAFloat(derecha);
        } else if (derecha.getTipo().getOperador() == TipoPR.PR_FLOAT) {
            this.tipo = derecha.getTipo();
            // castear izquierda a float
            this.izquierda = new EnteroAFloat(izquierda);
        }
        return tipo;
    }
}
