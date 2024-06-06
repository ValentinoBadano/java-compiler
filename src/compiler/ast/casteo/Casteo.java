package compiler.ast.casteo;

import compiler.ast.expresion.ExpresionLogica;

public abstract class Casteo extends ExpresionLogica {
    protected ExpresionLogica expresion;

    public Casteo(ExpresionLogica expresion) {
        this.expresion = expresion;
    }

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) + getExpresion().graficar(miId);
    }

    public ExpresionLogica getExpresion() {
        return expresion;
    }

    public void setExpresion(ExpresionLogica expresion) {
        this.expresion = expresion;
    }
}
