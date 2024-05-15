/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;


/**
 *
 * @author Mari
 */
public class TipoEntrada extends Expresion {
    
    public TipoPR operador;
    
    public TipoEntrada()
    {
      
    }
    public TipoEntrada(TipoPR operador)
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
            case PR_INPUT_INT:
               return "input_int_" + this.hashCode();
               
            case PR_INPUT_FLOAT:
                 return "input_float_" + this.hashCode();
                 
            case PR_INPUT_BOOL:
                 return "input_bool_" + this.hashCode();
                 
            case PR_INPUT_DUPLE:
                 return "input_duple_" + this.hashCode();
        }
        return null;

    }
    
}

