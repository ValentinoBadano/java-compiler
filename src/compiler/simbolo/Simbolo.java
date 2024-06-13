package compiler.simbolo;

import compiler.ast.TipoDato;

public class Simbolo {
    // Atributos del símbolo
    private final String name;  // Nombre del identificador
    private final String token; // Token asociado
    private final TipoDato type;  // Tipo de dato
    private final String value; // Valor del identificador (puede ser cualquier cosa)
    private final int length;   // Longitud (por ejemplo, para cadenas o arreglos)

    // Constructor para inicializar el símbolo
    public Simbolo(String name, String token, TipoDato type, String value, int length) {
        this.name = name;
        this.token = token;
        this.type = type;
        this.value = value;
        this.length = length;
    }

    // Métodos para obtener cada atributo
    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public TipoDato getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    // Representación en forma de cadena para mostrar información del símbolo
    @Override
    public String toString() {
        return String.format("Simbolo{NOMBRE='%s', TOKEN='%s', TIPO='%s', VALOR='%s', LONG=%d}", name, token, type, value, length);
    }
}
