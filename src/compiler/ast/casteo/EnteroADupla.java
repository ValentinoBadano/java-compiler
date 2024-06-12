package compiler.ast.casteo;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.expresion.ExpresionLogica;

public class EnteroADupla extends Casteo {


    public EnteroADupla(ExpresionLogica expresion) {
        super(expresion);
    }

    public String getId() {
        return "enterodupla_" + this.hashCode();
    }

    @Override
    public String toString() {
        return "(duple) " + expresion.toString();
    }

    public String getEtiqueta() {
        return "(duple)";
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.PR_DUPLE);
    }

    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        resultado.append(this.getExpresion().generarCodigo());

        return resultado.toString();
    }
}
