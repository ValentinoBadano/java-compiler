package compiler.simbolo;

import compiler.ast.Identificador;
import compiler.ast.TipoDato;

import java.util.Hashtable;
import java.util.List;

public class TablaSimbolos {

    public static Hashtable<String, Simbolo> ts = new Hashtable<>();

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



}
