/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class Identificador extends Expresion{
     private String nombre;

    public Identificador() {
    }

    public Identificador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
            this.nombre = nombre;
    }
    
    @Override 
    public String getEtiqueta(){
        return getNombre();
        
    }
    
    protected String getNombreOperacion() {
        return getNombre();
    }
    @Override
    protected String getId() {
        return "identificador_" + this.hashCode();
    }

    @Override
    public String toString() {
        return nombre;
    }
}
