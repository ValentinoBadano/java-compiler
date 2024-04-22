package compiler;

import java.util.Hashtable;

public class TablaSimbolos extends Hashtable<String, Simbolo> {



    // agrega varios simbolos a la tabla dada una string tipo "INT" y "a, b, c"
    public void add_symbols(String type, String identifiers) {
        String[] names = identifiers.split(",");

        for (String name : names) {
            name = name.trim(); // quitar espacios en blanco
            Simbolo symbol = new Simbolo(name, "ID", type, "", 0); // longitud nula

            this.put(name, symbol);
        }
    }

}
