package compiler;

public class Simbolo {
    // Atributos del símbolo
    private String name;  // Nombre del identificador
    private String token; // Token asociado
    private String type;  // Tipo de dato
    private String value; // Valor del identificador (puede ser cualquier cosa)
    private int length;   // Longitud (por ejemplo, para cadenas o arreglos)

    // Constructor para inicializar el símbolo
    public Simbolo(String name, String token, String type, String value, int length) {
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

    public String getType() {
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
