package compiler.simbolo;

import compiler.ast.Identificador;

public class SimboloNoExisteException extends Exception {
    private String id;
    public SimboloNoExisteException(Identificador id) {
        this.id = id.getNombre();
    }

    public SimboloNoExisteException(String id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "ERROR: el símbolo \"" + id + "\" no ha sido definido en la sección de declaración.\n";
    }
}
