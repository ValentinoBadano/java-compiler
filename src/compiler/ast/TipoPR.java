/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public enum TipoPR {
    PR_FLOAT,
    PR_INTEGER,
    PR_BOOLEAN,
    PR_DUPLE,
    STRING_LITERAL,
    PR_INPUT_INT,
    PR_INPUT_FLOAT,
    PR_INPUT_BOOL,
    PR_INPUT_DUPLE,
    PR_BREAK,
    PR_CONTINUE;
    
    
    
   @Override
    public String toString() {
        switch (this) {
            case PR_FLOAT:
                return "float";
            case PR_INTEGER:
                return "int";
            case PR_BOOLEAN:
                return "boolean";
            case PR_DUPLE:
                return "duple";
            case STRING_LITERAL:
                return "string";
             case PR_INPUT_INT:
                return "input_int";
            case PR_INPUT_FLOAT:
                return "input_float";
            case PR_INPUT_BOOL:
                return "input_bool";
            case PR_INPUT_DUPLE:
                return "input_duple";
            case PR_BREAK:
                return "break";
            case PR_CONTINUE:
                return "continue";
        }
        return "<null>";
    }

    
}
