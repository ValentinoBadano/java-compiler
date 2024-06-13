/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
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
        } else if (tipoIzq == TipoPR.PR_DUPLE && tipoDer == TipoPR.PR_FLOAT) {
            // Caso duple + float
            this.tipo = izquierda.getTipo();
            this.derecha = new FloatADupla(derecha); // castea derecha a duple
        } else if (tipoIzq == TipoPR.PR_FLOAT && tipoDer == TipoPR.PR_DUPLE) {
            // Caso float + duple
            this.tipo = derecha.getTipo();
            this.izquierda = new FloatADupla(izquierda); // castea izquierda a duple
        } else if (tipoIzq == TipoPR.PR_DUPLE && tipoDer == TipoPR.PR_INTEGER) {
            // Caso duple + integer
            this.tipo = izquierda.getTipo();
            this.derecha = new FloatADupla(new EnteroAFloat(derecha)); // castea derecha a float y luego a duple
        } else if (tipoIzq == TipoPR.PR_INTEGER && tipoDer == TipoPR.PR_DUPLE) {
            // Caso integer + duple
            this.tipo = derecha.getTipo();
            this.izquierda = new FloatADupla(new EnteroAFloat(izquierda)); // castea izquierda a float y luego a duple
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

        if (this.getTipo().getOperador() == TipoPR.PR_DUPLE) {
            // extraer los valores de la dupla izquierda
            String ptr1_dupla1 = this.izquierda.getIr_ref() + ".1";
            String ptr2_dupla1 = this.izquierda.getIr_ref() + ".2";
            // extraer los valores de la dupla derecha
            String ptr1_dupla2 = this.derecha.getIr_ref() + ".1";
            String ptr2_dupla2 = this.derecha.getIr_ref() + ".2";

            ConstanteDupla dupla = new ConstanteDupla(0.0, 0.0);
            resultado.append(dupla.generarCodigo());
            String ptr1 = dupla.getIr_ref() + ".1";
            String ptr2 = dupla.getIr_ref() + ".2";
            this.setIr_ref(dupla.getIr_ref());

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
