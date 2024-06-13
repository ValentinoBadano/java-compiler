package compiler.simbolo;

import compiler.ast.Identificador;
import compiler.ast.TipoDato;
import compiler.ast.TipoPR;
import compiler.ast.expresion.Declaracion;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TablaSimbolos {

    public static final Hashtable<String, Simbolo> ts = new Hashtable<>();

    // agrega varios simbolos a la tabla dada una string tipo "INT" y "a, b, c"
    public static void add_symbols(TipoDato type, List<Identificador> identifiers) throws SimboloExistenteException {
        for (Identificador id : identifiers) {
            String name = id.getNombre(); // quitar espacios en blanco
            Simbolo symbol = new Simbolo(name, "ID", type, "", 0); // longitud nula

            if (ts.containsKey(name)) {
                throw new SimboloExistenteException(name);
            };
            ts.put(name, symbol);
        }
    }

    public static void add_string_const(String str) {
        Simbolo symbol = new Simbolo("", "CTE_STR", null, str, str.length());
        ts.put(str, symbol);
    }

    public static boolean idExists(String id) {
        return ts.containsKey(id);
    }

    public static void reset() {
        ts.clear();
    }

    public static TipoDato getTipo(String id) {
        return ts.get(id).getType();
    }

    public static Declaracion getDeclaracion() {
        ArrayList<Identificador> declaraciones = new ArrayList<>();
        ts.forEach((k, v) -> {
            if (v.getType() != null) {
                declaraciones.add(new Identificador(v.getName()));
            }
        });

        return new Declaracion(new TipoDato(TipoPR.PR_INTEGER), declaraciones);
    }



}
