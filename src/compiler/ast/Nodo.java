/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public abstract class  Nodo {
    private String nombre;

    public Nodo() {}

    public Nodo(String nombre) {
        this.nombre = nombre;
    }

    protected String getId() {
        return "nodo_" + this.hashCode();
    }

    protected String getEtiqueta() {
        if (this.nombre != null) {
            return this.nombre;
        }
        final String name = this.getClass().getName();
        final int pos = name.lastIndexOf('.') + 1;
        return name.substring(pos);
    }
    
    protected String graficar(String idPadre){
        StringBuilder grafico = new StringBuilder();
        grafico.append(String.format("%1$s[label=\"%2$s\"]\n", this.getId(), this.getEtiqueta()));
        if(idPadre != null)
            grafico.append(String.format("%1$s--%2$s\n", idPadre, this.getId()));
        return grafico.toString();
    }

}
