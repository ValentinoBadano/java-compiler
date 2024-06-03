package compiler.ast.casteo;

import compiler.ast.Nodo;
import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.expresion.Expresion;
import compiler.ast.expresion.ExpresionLogica;
import compiler.llvm.CodeGeneratorHelper;

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
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        // TODO: hacer que funcione la conversión
        resultado.append(String.format("%vdestino = sitofp i32 %vorigen to float\n"));
        return resultado.toString();
    }


}
