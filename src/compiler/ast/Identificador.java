/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

import compiler.ast.expresion.Expresion;
import compiler.llvm.CodeGeneratorHelper;
import compiler.simbolo.TablaSimbolos;

/**
 *
 * @author Mari
 */
public class Identificador extends Expresion {
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
    protected String getEtiqueta() {
        return String.format("%s %s", getTipo().getOperador(), getNombre());
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

    public TipoDato getTipo() {
        return TablaSimbolos.getTipo(getNombre());
    }

    @Override
    public String generarCodigo() {
        TipoDato tipo = TablaSimbolos.getTipo(getNombre());
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        StringBuilder resultado = new StringBuilder();

        resultado.append(String.format("%3$s = load %1$s, %1$s* %2$s\n", tipo.getTipoLLVM(), "%" + getNombre(), this.getIr_ref()));

        return resultado.toString();
    }
}
