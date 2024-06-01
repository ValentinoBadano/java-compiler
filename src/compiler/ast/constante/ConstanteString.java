/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.constante;

/**
 *
 * @author Mari
 */
public class ConstanteString extends Constante{
    
     public ConstanteString(Object valor) {
        super(valor);
    }

    @Override
    protected String getEtiqueta() {
        return String.format(String.format("String %s", getValor()));
    }
    
    @Override
    protected String getId() {
        return "cte_string_" + this.hashCode();
    }
    
}
