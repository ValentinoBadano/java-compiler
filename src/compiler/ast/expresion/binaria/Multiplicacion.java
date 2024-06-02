/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.expresion.binaria;

import compiler.ast.expresion.ExpresionBinaria;
import compiler.ast.expresion.ExpresionLogica;

/**
 *
 * @author Mari
 */
public class Multiplicacion extends ExpresionBinaria {

    public Multiplicacion(ExpresionLogica izquierda, ExpresionLogica derecha) throws Exception {
        super(izquierda, derecha);
    }

    @Override
    public String get_llvm_op_code() {
        return "mul";
    }

    @Override
    protected String getNombreOperacion() {
        return "*";
    }
    
    @Override
    protected String getId() {
        return "multiplicacion_" + this.hashCode();
    }
}

