/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;


/**
 *
 * @author Mari
 */
public class TipoDato extends Expresion {
    
    public TipoPR operador;
    
    public TipoDato()
    {
      
    }
    public TipoDato(TipoPR operador)
    {
        this.operador = operador;
    }

    public TipoPR getOperador() {
        return operador;
    }

    public void setOperador(TipoPR operador) {
        this.operador = operador;
    }
    
      
    @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getOperador().toString());
    }
    
    @Override
    protected String graficar(String idPadre) {
        return super.graficar(idPadre);
        
    }
    
     @Override
    protected String getId() {
        switch (this.operador){
            case PR_FLOAT:
               return "float_" + this.hashCode();
               
            case PR_INTEGER:
                 return "integer_" + this.hashCode();
                 
            case PR_BOOLEAN:
                 return "booleano_" + this.hashCode();
                 
            case PR_DUPLE:
                 return "duple_" + this.hashCode();
                 
            case STRING_LITERAL:
                 return "string_" + this.hashCode();
                 
        }
        return null;

    }
    
}
