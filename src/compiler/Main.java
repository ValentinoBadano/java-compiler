/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import compiler.sintactico.Controller;

import javax.swing.*;
import java.io.IOException;

/**
 *
 * @author itt
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws IOException {

        try {
            // Establece el L&F deseado
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // TODO code application logic here

        CompilerInterface v = new CompilerInterface();
        Controller c = new Controller(v);
        c.init();
    }
}
