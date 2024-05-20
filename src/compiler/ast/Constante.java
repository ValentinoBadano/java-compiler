/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public abstract class Constante extends Expresion {
    private final Object valor;

    public Constante(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

    @Override
    protected String getEtiqueta() {
        return String.format(String.format("Const %s", getValor()));
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
