/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion.aritmetica;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.expresion.ExpresionLogica;
import compiler.ast.expresion.ExpresionUnaria;
import compiler.llvm.CodeGeneratorHelper;

/**
 *
 * @author Mari
 */
public class MenosUnario extends ExpresionUnaria {

    public MenosUnario(ExpresionLogica derecha) {
        super(derecha);
    }

   
    
    @Override
     protected String getNombreOperacion() {
            return "menosUnario_";
          
    }
    
    @Override
    protected String getId() {
        return "menosUnario_" + this.hashCode();
    }

    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        resultado.append(this.derecha.generarCodigo());
        resultado.append("\n");
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());

        if (this.getTipo().getOperador() == TipoPR.PR_FLOAT) {
            resultado.append(String.format("%1$s = fsub double 0.0, %2$s\n", this.getIr_ref(),
                    this.derecha.getIr_ref()));
        } else if (this.getTipo().getOperador() == TipoPR.PR_DUPLE) {
            // crea 2 punteros
            String ptr1 = CodeGeneratorHelper.getNewPointer();
            String ptr2 = CodeGeneratorHelper.getNewPointer();
            // carga los valores
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", ptr1, derecha.getIr_ref()));
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", ptr2, derecha.getIr_ref()));

            String val1 = CodeGeneratorHelper.getNewPointer();
            String val2 = CodeGeneratorHelper.getNewPointer();
            resultado.append(String.format("%1$s = load double, double* %2$s\n", val1, ptr1));
            resultado.append(String.format("%1$s = load double, double* %2$s\n", val2, ptr2));

            // crea 2 nuevos punteros
            String ptr1_dest = CodeGeneratorHelper.getNewPointer();
            String ptr2_dest = CodeGeneratorHelper.getNewPointer();

            // realiza las 2 operaciones
            resultado.append(String.format("%1$s = fsub double 0.0, %2$s\n", ptr1_dest, val1));
            resultado.append(String.format("%1$s = fsub double 0.0, %2$s\n", ptr2_dest, val2));

            // asigna el espacio para una nueva dupla
            resultado.append(String.format("%1$s = alloca %%struct.Tuple\n", this.getIr_ref()));
            // punteros temporales a los valores de la dupla
            String ptr1_res = CodeGeneratorHelper.getNewPointer();
            String ptr2_res = CodeGeneratorHelper.getNewPointer();
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", ptr1_res, getIr_ref()));
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", ptr2_res, getIr_ref()));
            // almacenar los valores de la dupla en la dupla destino
            resultado.append(String.format("store double %1$s, double* %2$s\n", ptr1_dest, ptr1_res));
            resultado.append(String.format("store double %1$s, double* %2$s\n", ptr2_dest, ptr2_res));
        } else {
            resultado.append(String.format("%1$s = sub i32 0, %2$s\n", this.getIr_ref(),
                    this.derecha.getIr_ref()));
        }





        return resultado.toString();
    }


    @Override
    public TipoDato getTipo() {
        return this.derecha.getTipo();
    }
}
