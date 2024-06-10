/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.constante;

import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.llvm.CodeGeneratorHelper;

/**
 *
 * @author Mari
 */
public class ConstanteDupla extends Constante{

    private double valor1;
    private double valor2;

    public ConstanteDupla(Object valor) {
        super(valor);
        setValores();
    }

    @Override
    protected String getEtiqueta() {
        return String.format(String.format("duple %s", getValor()));
    }
    
    @Override
    protected String getId() {
        return "cte_dupla_" + this.hashCode();
    }

    public TipoDato getTipo() {
        return new TipoDato(TipoPR.PR_DUPLE);
    }
    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        resultado.append(String.format("%1$s = alloca %%struct.Tuple\n", this.getIr_ref()));
        String ptr1 = CodeGeneratorHelper.getNewPointer();
        String ptr2 = CodeGeneratorHelper.getNewPointer();

        resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", ptr1, getIr_ref()));
        resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", ptr2, getIr_ref()));

        resultado.append(String.format("store double %1$s, double* %2$s\n", this.valor1, ptr1));
        resultado.append(String.format("store double %1$s, double* %2$s\n", this.valor2, ptr2));
        return resultado.toString();
    }

    public void setValores() {
        String values = this.getValor().toString();
        String[] parts = values.split(",");
        this.valor1 = Double.parseDouble(parts[0].substring(1));
        this.valor2 = Double.parseDouble(parts[1].substring(0, parts[1].length() - 1));
    }
}
