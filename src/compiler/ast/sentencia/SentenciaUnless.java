/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.sentencia;

import compiler.ast.expresion.ExpresionLogica;
import compiler.llvm.CodeGeneratorHelper;

/**
 *
 * @author Mari
 */
public class SentenciaUnless extends Sentencia{
    private ExpresionLogica expresion;
    private SentenciaUnlessThen sentenciasThen;
    private SentenciaUnlessElse sentenciasElse;
    
    
    public SentenciaUnless(ExpresionLogica expresion, SentenciaUnlessThen sentenciasThen) {
        this.expresion = expresion;
        this.sentenciasThen = sentenciasThen;
    }
    
    public SentenciaUnless(ExpresionLogica expresion, SentenciaUnlessThen sentenciasThen, SentenciaUnlessElse sentenciasElse ) {
        this.expresion = expresion;
        this.sentenciasThen = sentenciasThen;
        this.sentenciasElse = sentenciasElse;
    }

    public ExpresionLogica getExpresion() {
        return expresion;
    }

    public void setExpresion(ExpresionLogica expresion) {
        this.expresion = expresion;
    }
    
    protected String getNombreOperacion() {
        return "unless";
    }
    
    @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getNombreOperacion());
    }
    
    @Override
     protected String getId() {
          return "unless_" + this.hashCode();
    }
     
     @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) + getExpresion().graficar(miId)  + sentenciasThen.graficar(miId) + sentenciasElse.graficar(miId);
    }

    public String toString() {
        if (sentenciasElse == null) {
            return "unless " + expresion.toString() + " then " + sentenciasThen.toString() + " end";
        }
        return "unless " + expresion.toString() + " then " + sentenciasThen.toString() + " else " + sentenciasElse.toString() + " end";
    }

    public String generarCodigo() {
        final StringBuilder resultado = new StringBuilder();
        String tagEnd = CodeGeneratorHelper.getNewTag();

        resultado.append(expresion.generarCodigo());
        sentenciasElse.setTag(CodeGeneratorHelper.getNewTag());
        sentenciasThen.setTag(CodeGeneratorHelper.getNewTag());

        // condición
        resultado.append(String.format("br i1 %s, label %s, label %s\n", expresion.getIr_ref(),
                "%" + sentenciasElse.getTag(), "%" + sentenciasThen.getTag()));

        // Generar código para las sentencias then
        resultado.append(String.format("\n%s:\n", sentenciasThen.getTag()));
        resultado.append(sentenciasThen.generarCodigo());
        resultado.append(String.format("br label %s\n", "%" + tagEnd));

        // Generar código para las sentencias else
        resultado.append(String.format("\n%s:\n", sentenciasElse.getTag()));
        resultado.append(sentenciasElse.generarCodigo());
        resultado.append(String.format("br label %s\n", "%" + tagEnd));

        // etiqueta de fin
        resultado.append(String.format("\n%s:\n", tagEnd));

        return resultado.toString();
    }
    
    
    
    
    
}
