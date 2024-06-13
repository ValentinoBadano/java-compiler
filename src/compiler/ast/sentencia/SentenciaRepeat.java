/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.sentencia;

import compiler.llvm.CodeGeneratorHelper;

import java.util.List;

/**
 *
 * @author Mari
 */
public class SentenciaRepeat extends Sentencia{
    
     private List<Sentencia> sentenciasRepeat;
     private final SentenciaUntil until;

    public SentenciaRepeat(List<Sentencia> sentenciasRepeat, SentenciaUntil until) {
        this.sentenciasRepeat = sentenciasRepeat;
        this.until = until;
    }

    public List<Sentencia> getSentenciasRepeat() {
        return sentenciasRepeat;
    }

    public void setSentenciasRepeat(List<Sentencia> sentenciasRepeat) {
        this.sentenciasRepeat = sentenciasRepeat;
    }
    
     protected String getNombreOperacion() {
        return "repeat";
    }
    
    @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getNombreOperacion());
    }
    
    @Override
     protected String getId() {
          return "repeat_" + this.hashCode();
    }
    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        
        String graf = "";
        if(sentenciasRepeat != null){
              for(Sentencia sentenciaRepeat : sentenciasRepeat){
                  graf += sentenciaRepeat.graficar(miId);
            }
        }
        
        return super.graficar(idPadre) + graf + until.graficar(miId);
    }

    @Override
    public String toString() {
        return "repeat " + sentenciasRepeat.toString() + until.toString();
    }

    public String generarCodigo() {
        // TODO fibonacci en repeat
        StringBuilder resultado = new StringBuilder();

        String start = CodeGeneratorHelper.getNewTag();
        CodeGeneratorHelper.repeatStartFlags.push(start);
        String end = CodeGeneratorHelper.getNewTag();
        CodeGeneratorHelper.repeatEndFlags.push(end);

        resultado.append(String.format("br label %s\n", "%" + start));
        resultado.append("\n" + start + ":\n");

        CodeGeneratorHelper.setRepeat(true);

        if(sentenciasRepeat != null){
              for(Sentencia sentenciaRepeat : sentenciasRepeat){
                  resultado.append(sentenciaRepeat.generarCodigo());
            }
        }

        CodeGeneratorHelper.setRepeat(false);

        resultado.append(until.generarCodigo());

        resultado.append(String.format("br i1 %s, label %s, label %s\n", until.getExpresion().getIr_ref(),
                "%" + end, "%" + start));


        resultado.append("\n" + end + ":\n");

        CodeGeneratorHelper.repeatStartFlags.pop();
        CodeGeneratorHelper.repeatEndFlags.pop();

        return resultado.toString();
    }
}
