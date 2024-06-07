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
