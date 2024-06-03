/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.sentencia;

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

        resultado.append(this.getExpresion().generarCodigo());

        resultado.append(String.format("%1$s = call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @.integer, i32 0, i32 0), i32 %2$s)\n",
                CodeGeneratorHelper.getNewPointer(), this.getExpresion().getIr_ref()));
        return resultado.toString();
    }
}
