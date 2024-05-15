package compiler.simbolo;

import compiler.ast.Identificador;

import java.util.Hashtable;
import java.util.List;

public class TablaSimbolos extends Hashtable<String, Simbolo> {



    // agrega varios simbolos a la tabla dada una string tipo "INT" y "a, b, c"
    public void add_symbols(String type, List<Identificador> identifiers) {
        for (Identificador id : identifiers) {
            String name = id.getNombre(); // quitar espacios en blanco
            Simbolo symbol = new Simbolo(name, "ID", type, "", 0); // longitud nula

            this.put(name, symbol);
        }
    }

    public void add_string_const(String str) {
        Simbolo symbol = new Simbolo("", "CTE_STR", "", str, str.length());
        this.put(str, symbol);
    }

}
