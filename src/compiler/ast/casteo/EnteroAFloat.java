package compiler.ast.casteo;

import compiler.ast.Nodo;
import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.expresion.Expresion;
import compiler.ast.expresion.ExpresionLogica;
import compiler.llvm.CodeGeneratorHelper;

public class EnteroAFloat extends Casteo {

    public EnteroAFloat(ExpresionLogica expresion) {
        super(expresion);
    }

    public String getId() {
        return "enterofloat_" + this.hashCode();
    }

    @Override
    public String toString() {
        return "(float) " + expresion.toString();
    }

    public String getEtiqueta() {
        return "(float)";
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.PR_FLOAT);
    }

    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        resultado.append(this.getExpresion().generarCodigo());
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        // convierte la expresion a double
        resultado.append(String.format("%1$s = sitofp i32 %2$s to double\n", this.getIr_ref(), this.getExpresion().getIr_ref()));
        return resultado.toString();
    }
}
