package compiler.ast.casteo;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.expresion.ExpresionLogica;
import compiler.llvm.CodeGeneratorHelper;

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
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());

        // asigna el espacio para una nueva dupla
        String ptr1 = CodeGeneratorHelper.getNewPointer();
        String ptr2 = CodeGeneratorHelper.getNewPointer();
        resultado.append(String.format("%1$s = alloca %%struct.Tuple\n", this.getIr_ref()));

        // punteros temporales a los valores de la dupla
        resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", ptr1, getIr_ref()));
        resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", ptr2, getIr_ref()));

        // le asigna el valor de la expresion a los punteros
        resultado.append(String.format("store double %1$s, double* %2$s\n", this.getExpresion().getIr_ref(), ptr1));
        resultado.append(String.format("store double %1$s, double* %2$s\n", this.getExpresion().getIr_ref(), ptr2));
        return resultado.toString();
    }
}
