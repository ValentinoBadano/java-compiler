/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class ConstanteReal extends Constante {
    
    public ConstanteReal(Object valor) {
        super(valor);
    }

    @Override
    protected String getEtiqueta() {
        return String.format(String.format("Real %s", getValor()));
    }
    
     @Override
    protected String getId() {
        return "cte_real_" + this.hashCode();
    }
}
