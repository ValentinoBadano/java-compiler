/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class GeneracionCodigo extends Nodo{
    
    private final Programa programa;
    
    public GeneracionCodigo(Programa programa){
        this.programa = programa;
    }
    
    
    public String graficar() {
        
        // Acá se dispara la invocación a los métodos graficar() de los nodos.
        // Como la GeneracionCodigo no tiene padre, se inicia pasando null.  
        StringBuilder resultado = new StringBuilder();
        resultado.append("graph G {");
        resultado.append(this.programa.graficar(null));
        resultado.append(this.programa.graficar());
        resultado.append("}");
        return resultado.toString();
    }
    
    
}
