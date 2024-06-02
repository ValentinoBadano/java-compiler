/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

import compiler.ast.casteo.EnteroADupla;
import compiler.ast.casteo.EnteroAFloat;
import compiler.ast.casteo.FloatADupla;
import compiler.ast.expresion.ExpresionLogica;
import compiler.ast.sentencia.Sentencia;
import compiler.simbolo.TablaSimbolos;

/**
 *
 * @author Mari
 */
public class Asignacion extends Sentencia {
    
    private final Identificador identificador;
    private final ExpresionLogica expresion;

    public Asignacion(Identificador identificador, ExpresionLogica expresion) throws AsignacionTiposException {
        ExpresionLogica expresion1;
        this.identificador = identificador;
        expresion1 = expresion;

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
                    expresion1 = new EnteroAFloat(expresion); break;
                } else if (tipoExpresion.operador != TipoPR.PR_FLOAT) {
                    throw new AsignacionTiposException(tipoDeclarado, tipoExpresion, identificador);
                }
                break;
            case PR_DUPLE:
                switch (tipoExpresion.operador) {
                    case PR_BOOLEAN:
                        throw new AsignacionTiposException(tipoDeclarado, tipoExpresion, identificador);
                    case PR_INTEGER:
                        expresion1 = new EnteroADupla(expresion1);
                        break;
                    case PR_FLOAT:
                        expresion1 = new FloatADupla(expresion1); break;
                }
                break;
        }


        this.expresion = expresion1;
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
        resultado.append(expresion.generarCodigo() + "\n");

        resultado.append(String.format("store %1$s %2$s, %1$s* %3$s", tipo.getTipoLLVM(), expresion.getIr_ref(), "%" + identificador.getNombre()));

        return resultado.toString();
    }


}
