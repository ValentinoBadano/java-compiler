/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class Igualdad extends ExpresionBinariaLogica{

    public Igualdad(ExpresionLogica izquierda, ExpresionLogica derecha) {
        super(izquierda, derecha);
    }
    
    @Override
    protected String getNombreOperacion() {
        return "==";
    }
    
    @Override
    protected String getId() {
        return "igualdad_" + this.hashCode();
    }
}