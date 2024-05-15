/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class ConstanteDupla extends Constante{

    public ConstanteDupla(Object valor) {
        super(valor);
    }

    @Override
    protected String getEtiqueta() {
        return String.format(String.format("Dupla %s", getValor()));
    }
    
    @Override
    protected String getId() {
        return "cte_dupla_" + this.hashCode();
    }
}
