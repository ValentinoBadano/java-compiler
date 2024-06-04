package compiler.ast.casteo;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.expresion.ExpresionLogica;

public class FloatADupla extends ExpresionLogica{
    private final ExpresionLogica expresion;

    public FloatADupla (ExpresionLogica e) {
        this.expresion = e;
    }


    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) + getExpresion().graficar(miId);
    }

    public String getId() {
        return "floatdupla_" + this.hashCode();
    }

    public ExpresionLogica getExpresion() {
        return expresion;
    }

    @Override
    public String toString() {
        return "(duple) "  + expresion.toString();
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
        // TODO castear float a dupla
        resultado.append(String.format("%vdestino = fptosi float %vorigen to i32\n"));
        return resultado.toString();
    }
}
