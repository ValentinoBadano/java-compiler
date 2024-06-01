package compiler.simbolo;

public class SimboloExistenteError extends Error {
    private String name;
    public SimboloExistenteError(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "ERROR: El símbolo \"" + name + "\" ya ha sido definido";
    }
}
