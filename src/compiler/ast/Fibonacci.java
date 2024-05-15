/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler.ast;

/**
 *
 * @author Mari
 */
public class Fibonacci extends Expresion{
    
    private ExpresionLogica expresion;

    public Fibonacci() {
    }

    public Fibonacci(ExpresionLogica expresion) {
        this.expresion = expresion;
    }
    
    @Override
     protected String getId() {
          return "fibonacci" + this.hashCode();
    }
     
     
}
