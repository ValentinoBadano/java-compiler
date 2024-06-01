package compiler.ast.casteo;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.expresion.ExpresionLogica;

public class EnteroADupla extends ExpresionLogica {
    private final ExpresionLogica expresion;

    public EnteroADupla (ExpresionLogica e) {
        this.expresion = e;
    }


    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) + getExpresion().graficar(miId);
    }

    public String getId() {
        return "enterodupla_" + this.hashCode();
    }

    public ExpresionLogica getExpresion() {
        return expresion;
    }

    @Override
    public String toString() {
        return "duple";
    }

    public String getEtiqueta() {
        return "(duple)";
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.PR_DUPLE);
    }
}