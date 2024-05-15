/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class MenosUnario extends ExpresionUnaria{

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
}
