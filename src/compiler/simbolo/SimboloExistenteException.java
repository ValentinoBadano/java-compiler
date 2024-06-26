package compiler.simbolo;

public class SimboloExistenteException extends Exception {
    private final String name;
    public SimboloExistenteException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "ERROR: El símbolo \"" + name + "\" ya ha sido definido\n";
    }
}
