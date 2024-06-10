package compiler.ast.expresion.logica;

import compiler.ast.TipoPR;
import compiler.ast.casteo.EnteroAFloat;
import compiler.ast.expresion.ExpresionBinariaLogica;
import compiler.ast.expresion.ExpresionLogica;
import compiler.llvm.CodeGeneratorHelper;

public abstract class Comparacion extends ExpresionBinariaLogica {

    public Comparacion(ExpresionLogica izquierda, ExpresionLogica derecha) throws Exception {
        super(izquierda, derecha);
        validarTipo();
    }


    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        resultado.append(this.izquierda.generarCodigo());
        resultado.append("\n");
        resultado.append(this.derecha.generarCodigo());
        resultado.append("\n");
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        resultado.append(String.format("%s = %s %s %s, %s\n",
                this.getIr_ref(),
                this.get_llvm_op_code(this.tipo.operador),
                this.izquierda.getTipo().getTipoLLVM(),
                this.izquierda.getIr_ref(),
                this.derecha.getIr_ref()
        ));

        return resultado.toString();
    }

    public void validarTipo() throws Exception {
        TipoPR tipoIzq = izquierda.getTipo().getOperador();
        TipoPR tipoDer = derecha.getTipo().getOperador();

        if (tipoDer == tipoIzq && tipoIzq != TipoPR.PR_DUPLE) {
            // Caso mismo tipo
            this.tipo = izquierda.getTipo();
        } else if (tipoIzq == TipoPR.PR_FLOAT && tipoDer == TipoPR.PR_INTEGER) {
            // Caso float + integer
            this.tipo = izquierda.getTipo();
            this.derecha = new EnteroAFloat(derecha); // castea derecha a float
        } else if (tipoIzq == TipoPR.PR_INTEGER && tipoDer == TipoPR.PR_FLOAT) {
            // Caso integer + float
            this.tipo = derecha.getTipo();
            this.izquierda = new EnteroAFloat(izquierda); // castea izquierda a float
        } else {
            throw new Exception(String.format("ERROR: Incompatibilidad de tipos en la operación %s %s %s%n", izquierda, getNombreOperacion(), derecha));
        }
    }

    public abstract String get_llvm_op_code(TipoPR tipo);
}
