package compiler.simbolo;

import compiler.ast.Identificador;

public class SimboloNoExisteException extends Exception {
    private Identificador id;
    public SimboloNoExisteException(Identificador id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Error: el símbolo \"" + this.id.getNombre() + "\" no ha sido definido en la sección de declaración.";
    }
}
