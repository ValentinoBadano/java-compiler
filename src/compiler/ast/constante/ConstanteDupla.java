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
    private String ptro1;
    private String ptro2;


    public ConstanteDupla(Object valor) {
        super(valor);
        setValores();
    }

    public ConstanteDupla(double valor1, double valor2) {
        super(valor1 + "," + valor2);
        this.valor1 = valor1;
        this.valor2 = valor2;
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
        ptro1 = this.getIr_ref() + ".1";
        ptro2 = this.getIr_ref() + ".2";

        resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", ptro1, getIr_ref()));
        resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", ptro2, getIr_ref()));

        resultado.append(String.format("store double %1$s, double* %2$s\n", this.valor1, ptro1));
        resultado.append(String.format("store double %1$s, double* %2$s\n", this.valor2, ptro2));
        return resultado.toString();
    }

    public void setValores() {
        String values = this.getValor().toString();
        String[] parts = values.split(",");
        this.valor1 = Double.parseDouble(parts[0].substring(1));
        this.valor2 = Double.parseDouble(parts[1].substring(0, parts[1].length() - 1));
    }

    public double getValor1() {
        return valor1;
    }

    public double getValor2() {
        return valor2;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", valor1, valor2);
    }

}
