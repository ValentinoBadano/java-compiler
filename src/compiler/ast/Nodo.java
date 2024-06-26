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
    private String ir_ref;
    protected String color;

    public Nodo() {}

    public Nodo(String nombre) {
        this.nombre = nombre;
        this.color = "black";
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
    
    public String graficar(String idPadre){
        StringBuilder grafico = new StringBuilder();
        grafico.append(String.format("%1$s[label=\"%2$s\", color=\"%3$s\"]\n", this.getId(), this.getEtiqueta(), this.color));
        if(idPadre != null)
            grafico.append(String.format("%1$s--%2$s\n", idPadre, this.getId()));
        return grafico.toString();
    }

    public String generarCodigo() {
        return "";
    };

    public String getIr_ref() {
        return ir_ref;
    }

    public void setIr_ref(String ir_ref) {
        this.ir_ref = ir_ref;
    }

}
