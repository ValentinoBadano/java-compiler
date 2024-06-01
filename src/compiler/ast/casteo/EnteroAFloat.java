package compiler.ast.casteo;

import compiler.ast.Nodo;
import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.expresion.Expresion;
import compiler.ast.expresion.ExpresionLogica;

public class EnteroAFloat extends ExpresionLogica {
    private final ExpresionLogica expresion;

    public EnteroAFloat(ExpresionLogica e) {
        this.expresion = e;
    }

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) + getExpresion().graficar(miId);
    }

    public String getId() {
        return "enterofloat_" + this.hashCode();
    }

    public ExpresionLogica getExpresion() {
        return expresion;
    }

    @Override
    public String toString() {
        return "float";
    }

    public String getEtiqueta() {
        return "(float)";
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.PR_FLOAT);
    }
}
