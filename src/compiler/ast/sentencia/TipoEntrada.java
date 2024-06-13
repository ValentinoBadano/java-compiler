/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast.sentencia;


import compiler.ast.Asignacion;
import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.constante.ConstanteDupla;
import compiler.ast.expresion.Expresion;
import compiler.llvm.CodeGeneratorHelper;

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
        this.tipo = getTipo();
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
    public String graficar(String idPadre) {
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

    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        String temp = CodeGeneratorHelper.getNewPointer();


        switch (tipo.getOperador()) {
            case PR_INTEGER:
                resultado.append(String.format("%s = alloca i32\n", temp));
                resultado.append(String.format("%s = call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @" +
                        "int_read_format, i64 0, i64 0), i32* %s)\n", CodeGeneratorHelper.getNewPointer(), temp)); break;
            case PR_FLOAT:
                resultado.append(String.format("%s = alloca double\n", temp));
                resultado.append(String.format("%s = call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @" +
                        "double_read_format, i64 0, i64 0), double* %s)\n", CodeGeneratorHelper.getNewPointer(), temp)); break;
            case PR_BOOLEAN:
                resultado.append(String.format("%s = alloca i32\n", temp));
                resultado.append(String.format("%s = call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @" +
                        "bool_read_format, i64 0, i64 0), i32* %s)\n", CodeGeneratorHelper.getNewPointer(), temp)); break;
            case PR_DUPLE:
                resultado.append(String.format("%1$s = alloca %%struct.Tuple\n", this.getIr_ref()));
                String ptr1 = CodeGeneratorHelper.getNewPointer();
                String ptr2 = CodeGeneratorHelper.getNewPointer();

                resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", ptr1, getIr_ref()));
                resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", ptr2, getIr_ref()));

                // asigna el primer valor de la dupla
                resultado.append(String.format("%s = call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @" +
                        "double_read_format, i64 0, i64 0), double* %s)\n", CodeGeneratorHelper.getNewPointer(), ptr1));
                // asigna el segundo valor de la dupla
                resultado.append(String.format("%s = call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @" +
                        "double_read_format, i64 0, i64 0), double* %s)\n", CodeGeneratorHelper.getNewPointer(), ptr2));
                return resultado.toString();
        }

        resultado.append(String.format("%1$s = load %2$s, %2$s* %3$s\n", this.getIr_ref(), this.getTipo().getTipoLLVM(), temp));
        return resultado.toString();
    }

    @Override
    public TipoDato getTipo() {
        switch (this.operador){
            case PR_INPUT_INT:
                return new TipoDato(TipoPR.PR_INTEGER);
            case PR_INPUT_FLOAT:
                return new TipoDato(TipoPR.PR_FLOAT);
            case PR_INPUT_BOOL:
                return new TipoDato(TipoPR.PR_BOOLEAN);
            case PR_INPUT_DUPLE:
                return new TipoDato(TipoPR.PR_DUPLE);
        }
        return null;
    }
    
}

