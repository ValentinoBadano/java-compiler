package compiler.ast.casteo;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.constante.ConstanteDupla;
import compiler.ast.expresion.ExpresionLogica;

public class FloatADupla extends Casteo{

    public FloatADupla(ExpresionLogica expresion) {
        super(expresion);
    }

    public String getId() {
        return "floatdupla_" + this.hashCode();
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
        resultado.append(this.getExpresion().generarCodigo());

        ConstanteDupla dupla = new ConstanteDupla(0.0, 0.0);
        dupla.generarCodigo();
        this.setIr_ref(dupla.getIr_ref());
        // asigna el espacio para una nueva dupla
        String ptr1 = this.getIr_ref() + ".1";
        String ptr2 = this.getIr_ref() + ".2";

        resultado.append(String.format("%1$s = alloca %%struct.Tuple\n", this.getIr_ref()));

        // punteros temporales a los valores de la dupla
        resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", ptr1, getIr_ref()));
        resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", ptr2, getIr_ref()));

        // le asigna el valor de la expresion a los punteros
        resultado.append(String.format("store double %1$s, double* %2$s\n", this.getExpresion().getIr_ref(), dupla.getIr_ref() + ".1"));
        resultado.append(String.format("store double %1$s, double* %2$s\n", this.getExpresion().getIr_ref(), dupla.getIr_ref() + ".2"));

        String ptrov1 = getIr_ref() + ".v1";
        String ptrov2 = getIr_ref() + ".v2";

        resultado.append(String.format("%1$s = load double, double* %2$s\n", ptrov1, ptr1));
        resultado.append(String.format("%1$s = load double, double* %2$s\n", ptrov2, ptr2));
        return resultado.toString();
    }
}
