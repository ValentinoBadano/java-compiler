/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion;

import compiler.ast.TipoPR;
import compiler.llvm.CodeGeneratorHelper;

/**
 *
 * @author Mari
 */
public abstract class ExpresionBinariaLogica extends ExpresionLogica {
    protected ExpresionLogica izquierda;
    protected  ExpresionLogica derecha;

    public ExpresionBinariaLogica(ExpresionLogica izquierda, ExpresionLogica derecha) throws Exception {
        this.izquierda = izquierda;
        this.derecha = derecha;
        validarTipo();
    }


    public abstract String get_llvm_op_code(TipoPR tipo);

    @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getNombreOperacion());
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
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        resultado.append(this.izquierda.generarCodigo());
        resultado.append("\n");
        resultado.append(this.derecha.generarCodigo());
        resultado.append("\n");
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        resultado.append(String.format("%1$s = %2$s %3$s %4$s, %5$s\n", this.getIr_ref(),
                this.get_llvm_op_code(this.tipo.operador), this.getTipo().getTipoLLVM() , this.izquierda.getIr_ref(),
                this.derecha.getIr_ref()));

        return resultado.toString();
    }

    public void validarTipo() throws Exception {
        TipoPR tipoIzq = izquierda.getTipo().getOperador();
        TipoPR tipoDer = derecha.getTipo().getOperador();

        if (tipoDer == TipoPR.PR_BOOLEAN && tipoIzq == TipoPR.PR_BOOLEAN) {
            this.tipo = izquierda.getTipo();
        } else {
            throw new Exception(String.format("ERROR: Incompatibilidad de tipos en la operación booleana (%s) %s (%s)%n", izquierda, getNombreOperacion(), derecha));
        }
    }

}
