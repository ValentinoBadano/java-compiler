/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.casteo.EnteroAFloat;
import compiler.llvm.CodeGeneratorHelper;

/**
 *
 * @author Mari
 */
public abstract class ExpresionBinaria extends Expresion{
    protected ExpresionLogica izquierda;
    protected ExpresionLogica derecha;

    public ExpresionBinaria(ExpresionLogica izquierda, ExpresionLogica derecha) throws Exception {
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.validarTipo();
    }

    public abstract String get_llvm_op_code();


    @Override
    protected String getEtiqueta() {
        return String.format("%s %s", getTipo().getOperador(), this.getNombreOperacion());
    }

    protected abstract String getNombreOperacion();

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) +
                izquierda.graficar(miId) +
                derecha.graficar(miId);
    }

    @Override
    public String toString() {
        return izquierda + " " + this.getNombreOperacion() + " " + derecha;
    }

    @Override
    public TipoDato getTipo() {
        return tipo;
    }

    public void validarTipo() throws Exception {
        TipoPR tipoIzq = izquierda.getTipo().getOperador();
        TipoPR tipoDer = derecha.getTipo().getOperador();

        if (tipoDer == tipoIzq) {
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
            // TODO caso boolean? casos duple
        } else {
            throw new Exception(String.format("ERROR: Incompatibilidad de tipos en la operación %s %s %s%n", izquierda, getNombreOperacion(), derecha));
        }
    }

    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        resultado.append(this.izquierda.generarCodigo());
        resultado.append("\n");
        resultado.append(this.derecha.generarCodigo());
        resultado.append("\n");
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        resultado.append(String.format("%1$s = %6$s%2$s %3$s %4$s, %5$s\n", this.getIr_ref(),
                this.get_llvm_op_code(), this.getTipo().getTipoLLVM() , this.izquierda.getIr_ref(),
                this.derecha.getIr_ref(), isFloat()));

        return resultado.toString();
    }

    public String isFloat() {
        if (this.getTipo().getOperador() == TipoPR.PR_FLOAT)
            return "f";
        return "";
    }
}
