/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.sentencia;

import compiler.ast.TipoPR;
import compiler.ast.constante.ConstanteString;
import compiler.ast.expresion.ExpresionLogica;
import compiler.llvm.CodeGeneratorHelper;

/**
 *
 * @author Mari
 */
public class SentenciaShow extends Sentencia{
    
    private ExpresionLogica expresion;
    private ConstanteString constanteString;
    
     public SentenciaShow() {
    }

    public SentenciaShow(ExpresionLogica expresion) {
        this.expresion = expresion;
    }

    public SentenciaShow(ConstanteString c) {
        this.constanteString = c;
    }

    public ExpresionLogica getContenido() {
        return expresion;
    }

    public void setContenido(ExpresionLogica expresion) {
        this.expresion = expresion;
    }

    public ConstanteString getConstanteString() {
        return constanteString;
    }

    public void setConstanteString(ConstanteString constanteString) {
        this.constanteString = constanteString;
    }
    
    @Override
    protected String getEtiqueta() {
        return "Show";
    }
    
    @Override
     protected String getId() {
          return "show_" + this.hashCode();
    }

    public int getStrLength() {
        return this.getConstanteString().getLength() + 1;
    }
    
    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        
        if(expresion != null){
            return super.graficar(idPadre) + getContenido().graficar(miId);
        }
        else{
            return super.graficar(idPadre) + getConstanteString().graficar(miId);
        }        
    }

    @Override
    public String toString() {
         if (expresion == null) {
             return "show(" + constanteString.toString() + ")";
        }
        return "show(" + expresion + ")";
    }

    public ExpresionLogica getExpresion() {
        return expresion;
    }

    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();

        if (this.constanteString != null) {
            // caso show(String)
            resultado.append(this.getConstanteString().generarCodigo());
            resultado.append(String.format("%1$s = call i32 @printf(i8* getelementptr ([%2$s x i8], [%2$s x i8]* %3$s, i32 0, i32 0))\n",
                    CodeGeneratorHelper.getNewPointer(), getStrLength(), this.getConstanteString().getIr_ref()));
        } else {
            // caso show(expresion) imprime @integer y @float
            // TODO imprimir booleanos
            resultado.append(this.getExpresion().generarCodigo());
            resultado.append(String.format("%1$s = call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @.%4$s, i32 0, i32 0), %3$s %2$s)\n",
                    CodeGeneratorHelper.getNewPointer(), this.getExpresion().getIr_ref(), this.getExpresion().getTipo().getTipoLLVM(), this.getExpresion().getTipo().getOperador()));
        }
        // TODO: manejar otros tipos de show (boolean, duplas)
        return resultado.toString();
    }
}
