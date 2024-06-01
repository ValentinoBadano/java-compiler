package compiler.ast;

public class AsignacionTiposException extends Exception{
    private final TipoDato declarado;
    private final TipoDato asignado;
    private final Identificador id;

    public AsignacionTiposException(TipoDato declarado, TipoDato asignado, Identificador id) {
        this.declarado = declarado;
        this.asignado = asignado;
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("ERROR: La variable '%s' es de tipo %s pero se le intentó asignar una expresión de tipo %s.", id, declarado, asignado);
    }
}
