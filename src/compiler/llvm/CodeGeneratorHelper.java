package compiler.llvm;

import java.util.Stack;

/**
 *
 * @author Valentino
 */
public class CodeGeneratorHelper {
    
    private static int nextID = 0;
    private static boolean isRepeat = false;
    public static final Stack<String> repeatStartFlags = new Stack<>();
    public static final Stack<String> repeatEndFlags = new Stack<>();
        
    private CodeGeneratorHelper(){}
   
    public static String getNewPointer(){
        StringBuilder ret = new StringBuilder();
        nextID+=1;
        ret.append(String.format("%%ptro.%s", nextID));
        return ret.toString();
    }
    
    public static String getNewGlobalPointer(){
        StringBuilder ret = new StringBuilder();
        nextID+=1;
        ret.append(String.format("@gb.%s", nextID));
        return ret.toString();
    }
    
    public static String getNewTag(){
        StringBuilder ret = new StringBuilder();
        nextID+=1;
        ret.append(String.format("tag.%s", nextID));
        return ret.toString();
    }

    public static boolean isRepeat() {
        return isRepeat;
    }

    public static void setRepeat(boolean isRepeat) {
        CodeGeneratorHelper.isRepeat = isRepeat;
    }
}
