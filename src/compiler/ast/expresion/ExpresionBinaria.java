/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.casteo.EnteroADupla;
import compiler.ast.casteo.EnteroAFloat;
import compiler.ast.casteo.FloatADupla;
import compiler.ast.constante.ConstanteDupla;
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

    protected ExpresionBinaria() {
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
            this.tipo = izquierda.getTipo();
        } else {
            if (tipoIzq == TipoPR.PR_FLOAT && tipoDer == TipoPR.PR_INTEGER) {
                this.derecha = new EnteroAFloat(derecha);
            } else if (tipoIzq == TipoPR.PR_INTEGER && tipoDer == TipoPR.PR_FLOAT) {
                this.izquierda = new EnteroAFloat(izquierda);
            } else if (tipoIzq == TipoPR.PR_DUPLE && tipoDer == TipoPR.PR_FLOAT) {
                this.derecha = new FloatADupla(derecha);
            } else if (tipoIzq == TipoPR.PR_FLOAT && tipoDer == TipoPR.PR_DUPLE) {
                this.izquierda = new FloatADupla(izquierda);
            } else if (tipoIzq == TipoPR.PR_DUPLE && tipoDer == TipoPR.PR_INTEGER) {
                this.derecha =  new EnteroADupla(derecha);
            } else if (tipoIzq == TipoPR.PR_INTEGER && tipoDer == TipoPR.PR_DUPLE) {
                this.izquierda = new EnteroADupla(izquierda);
            } else {
                throw new Exception(String.format("ERROR: Incompatibilidad de tipos en la operación %s %s %s%n", izquierda, getNombreOperacion(), derecha));
            }
            this.tipo = (tipoIzq == TipoPR.PR_INTEGER) ? derecha.getTipo() : izquierda.getTipo();
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

        if (this.getTipo().getOperador() == TipoPR.PR_DUPLE) {
            String ptr1_dupla1 = CodeGeneratorHelper.getNewPointer();
            String ptr2_dupla1 = CodeGeneratorHelper.getNewPointer();
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", ptr1_dupla1, izquierda.getIr_ref()));
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", ptr2_dupla1, izquierda.getIr_ref()));
            // extraer los valores de la dupla derecha
            String ptr1_dupla2 = CodeGeneratorHelper.getNewPointer();
            String ptr2_dupla2 = CodeGeneratorHelper.getNewPointer();
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", ptr1_dupla2, derecha.getIr_ref()));
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", ptr2_dupla2, derecha.getIr_ref()));

            // asigna el espacio para una nueva dupla
            resultado.append(String.format("%1$s = alloca %%struct.Tuple\n", this.getIr_ref()));
            // punteros temporales a los valores de la dupla
            String ptr1 = CodeGeneratorHelper.getNewPointer();
            String ptr2 = CodeGeneratorHelper.getNewPointer();
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", ptr1, getIr_ref()));
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", ptr2, getIr_ref()));

            // carga los valores de las duplas en 2 nuevas variables temporales con load
            String val1_dupla1 = CodeGeneratorHelper.getNewPointer();
            String val2_dupla1 = CodeGeneratorHelper.getNewPointer();
            resultado.append(String.format("%1$s = load double, double* %2$s\n", val1_dupla1, ptr1_dupla1));
            resultado.append(String.format("%1$s = load double, double* %2$s\n", val2_dupla1, ptr2_dupla1));
            String val1_dupla2 = CodeGeneratorHelper.getNewPointer();
            String val2_dupla2 = CodeGeneratorHelper.getNewPointer();
            resultado.append(String.format("%1$s = load double, double* %2$s\n", val1_dupla2, ptr1_dupla2));
            resultado.append(String.format("%1$s = load double, double* %2$s\n", val2_dupla2, ptr2_dupla2));

            // crea 2 punteros para almacenar los valores de las duplas
            String ptr1_dest = CodeGeneratorHelper.getNewPointer();
            String ptr2_dest = CodeGeneratorHelper.getNewPointer();

            // realiza las 2 operaciones
            resultado.append(String.format("%1$s = %2$s %3$s %4$s, %5$s\n",
                    ptr1_dest, this.get_llvm_op_code(), "double", val1_dupla1, val1_dupla2));
            resultado.append(String.format("%1$s = %2$s %3$s %4$s, %5$s\n",
                    ptr2_dest, this.get_llvm_op_code(), "double", val2_dupla1, val2_dupla2));
            // almacenar los valores de la dupla en la dupla destino
            resultado.append(String.format("store double %1$s, double* %2$s\n", ptr1_dest, ptr1));
            resultado.append(String.format("store double %1$s, double* %2$s\n", ptr2_dest, ptr2));

        } else {
            // caso normal
            resultado.append(String.format("%1$s = %2$s %3$s %4$s, %5$s\n", this.getIr_ref(),
                    this.get_llvm_op_code(), this.getTipo().getTipoLLVM(), izquierda.getIr_ref(),
                    derecha.getIr_ref()));
        }


        return resultado.toString();
    }
}
