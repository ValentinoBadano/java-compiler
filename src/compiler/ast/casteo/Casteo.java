package compiler.ast.casteo;

import compiler.ast.expresion.ExpresionLogica;

public abstract class Casteo extends ExpresionLogica {
    protected ExpresionLogica expresion;

    public Casteo(ExpresionLogica expresion) {
        this.expresion = expresion;
        this.color = "blue";
    }

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        String s = super.graficar(idPadre) +  getExpresion().graficar(miId);
        return s;
    }

    public ExpresionLogica getExpresion() {
        return expresion;
    }

    public void setExpresion(ExpresionLogica expresion) {
        this.expresion = expresion;
    }
}
