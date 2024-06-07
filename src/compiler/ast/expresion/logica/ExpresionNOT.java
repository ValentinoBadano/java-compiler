/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion.logica;

import compiler.ast.expresion.ExpresionLogica;
import compiler.ast.expresion.ExpresionUnariaLogica;
import compiler.llvm.CodeGeneratorHelper;

/**
 *
 * @author Mari
 */
public class ExpresionNOT extends ExpresionUnariaLogica {
    
    public ExpresionNOT(ExpresionLogica derecha) {
        super(derecha);
    }



    @Override
     protected String getNombreOperacion() {
            return "not";
    }
     
    @Override
    protected String getId() {
        return "exp_not_" + this.hashCode();
    }

    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        resultado.append(this.derecha.generarCodigo());
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        resultado.append(String.format("%1$s = xor i1 1, %2$s\n", this.getIr_ref(), this.derecha.getIr_ref()));
        return resultado.toString();
    }
}
