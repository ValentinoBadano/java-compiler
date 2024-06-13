/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

import compiler.ast.casteo.EnteroAFloat;
import compiler.ast.casteo.FloatADupla;
import compiler.ast.expresion.ExpresionLogica;
import compiler.ast.sentencia.Sentencia;
import compiler.llvm.CodeGeneratorHelper;
import compiler.simbolo.TablaSimbolos;

/**
 *
 * @author Mari
 */
public class Asignacion extends Sentencia {
    
    private final Identificador identificador;
    private final ExpresionLogica expresion;

    public Asignacion(Identificador identificador, ExpresionLogica expresion) throws AsignacionTiposException {
        this.identificador = identificador;
        ExpresionLogica expresion_temporal = expresion;

        TipoDato tipoDeclarado = TablaSimbolos.getTipo(identificador.getNombre());
        TipoDato tipoExpresion = expresion.getTipo();

        switch (tipoDeclarado.operador) {
            case PR_INTEGER:
            case PR_BOOLEAN:
                if (tipoExpresion.operador != tipoDeclarado.operador) {
                    throw new AsignacionTiposException(tipoDeclarado, tipoExpresion, identificador);
                }
                break;
            case PR_FLOAT:
                if (tipoExpresion.operador == TipoPR.PR_INTEGER ) {
                    expresion_temporal = new EnteroAFloat(expresion); break;
                } else if (tipoExpresion.operador != TipoPR.PR_FLOAT) {
                    throw new AsignacionTiposException(tipoDeclarado, tipoExpresion, identificador);
                }
                break;
            case PR_DUPLE:
                switch (tipoExpresion.operador) {
                    case PR_BOOLEAN:
                        throw new AsignacionTiposException(tipoDeclarado, tipoExpresion, identificador);
                    case PR_INTEGER:
                        expresion_temporal = new FloatADupla(new EnteroAFloat(expresion_temporal));
                        break;
                    case PR_FLOAT:
                        expresion_temporal = new FloatADupla(expresion_temporal); break;
                }
                break;
        }


        this.expresion = expresion_temporal;
    }

    public Identificador getIdentificador() {
        return identificador;
    }

    public ExpresionLogica getExpresion() {
        return expresion;
    }



    
    @Override
    protected String getEtiqueta() {
        return ":=";
    }
    
      @Override
    protected String getId() {
        return "asignacion_" + this.hashCode();
    }
    
    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) +
                identificador.graficar(miId) +
                expresion.graficar(miId);
    }

    @Override
    public String toString() {
        return identificador + ":= " + expresion;
    }

    @Override
    public String generarCodigo() {
        TipoDato tipo = TablaSimbolos.getTipo(identificador.getNombre());
        StringBuilder resultado = new StringBuilder();
        resultado.append(expresion.generarCodigo());

        if (tipo.operador == TipoPR.PR_DUPLE) {
            // extraer los valores de la dupla
            String prt1_origen = CodeGeneratorHelper.getNewPointer();
            String prt2_origen = CodeGeneratorHelper.getNewPointer();
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", prt1_origen, expresion.getIr_ref()));
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", prt2_origen, expresion.getIr_ref()));

            // cargar los valores de la dupla en variables temporales
            String ptr1_temp = CodeGeneratorHelper.getNewPointer();
            String ptr2_temp = CodeGeneratorHelper.getNewPointer();
            resultado.append(String.format("%1$s = load double, double* %2$s\n", ptr1_temp, prt1_origen));
            resultado.append(String.format("%1$s = load double, double* %2$s\n", ptr2_temp, prt2_origen));

            // crear punteros para la dupla destino
            String ptr1_dest = CodeGeneratorHelper.getNewPointer();
            String ptr2_dest = CodeGeneratorHelper.getNewPointer();
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 0\n", ptr1_dest, "%" + identificador.getNombre()));
            resultado.append(String.format("%1$s = getelementptr %%struct.Tuple, %%struct.Tuple* %2$s, i32 0, i32 1\n", ptr2_dest, "%" + identificador.getNombre()));

            // almacenar los valores de la dupla en la dupla destino
            resultado.append(String.format("store double %1$s, double* %2$s\n", ptr1_temp, ptr1_dest));
            resultado.append(String.format("store double %1$s, double* %2$s\n", ptr2_temp, ptr2_dest));
        } else {
            // si no es una dupla, se almacena el valor de la expresion en la variable
            resultado.append(String.format("store %1$s %2$s, %1$s* %3$s", tipo.getTipoLLVM(), expresion.getIr_ref(), "%" + identificador.getNombre()));
            resultado.append("\n");
        }
        return resultado.toString();
    }


}
