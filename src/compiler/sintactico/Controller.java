package compiler.sintactico;

import compiler.CompilerInterface;
import compiler.simbolo.*;
import compiler.lexico.*;
import compiler.ast.*;
import java_cup.runtime.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements ActionListener {

    private final CompilerInterface view;

    public Controller(CompilerInterface view) {
        this.view = view;
    }

    public void init() {
        view.getLexicButton().addActionListener(this);
        view.getCleanButton().addActionListener(this);
        view.getSyntacticButton().addActionListener(this);
        view.getAstMenuItem().addActionListener(this);
        view.getSymbolTableItem().addActionListener(this);
        view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "lexico":this.doLexicalAnalysis(); break;
            case "syntactic": this.doSyntacticAnalysis(); break;
            case "ast":this.doAstAnalysis();break;
            case "table":this.generateSymbolTableFile();break;
            case "Borrar": this.clean();
        }
    }

    public void doLexicalAnalysis() {
        String code = view.getCodeTextArea().getText();
        MiLexico lexico = new MiLexico(new StringReader(code));
        view.clearOutput();

        while (true) {
            Symbol token = null;
            try {
                token = lexico.next_token();
            } catch (Error | IOException e) {
                view.appendTextOutput("ERROR: " + e.getMessage());
                return;
            }
            if (Objects.equals(token.toString(), "#0"))
                break;
            view.appendTextOutput("Token: " + token + "\n");
        }

        view.appendTextOutput("Análisis léxico finalizado con éxito.");
    }

    public void doSyntacticAnalysis() {
        view.clearOutput();
        List<String> output = new ArrayList<>();
        Hashtable<String, Simbolo> table = new Hashtable<>();

        try {
            String code = view.getCodeTextArea().getText();
            MiLexico lexico = new MiLexico(new StringReader(code));
            Parser parser = new Parser(lexico);
            parser.parse();
            output = parser.action_obj.output;
            table = parser.action_obj.table;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            output.add(ex.getMessage());
        }

        for (String line: output
             ) {
            view.appendTextOutput(line + "\n");
        }


    }
    
    public void doAstAnalysis() {
        view.clearOutput();

        List<String> output = new ArrayList<>();
        Hashtable<String, Simbolo> table = new Hashtable<>();
    
      
      
        try {
            String code = view.getCodeTextArea().getText();
            MiLexico lexico = new MiLexico(new StringReader(code));
            Parser parser = new Parser(lexico);
            final GeneracionCodigo gc = (GeneracionCodigo) parser.parse().value;
          
     
            parser.parse();
            output = parser.action_obj.output;
            table = parser.action_obj.table;
            PrintWriter grafico = new PrintWriter(new FileWriter("arbol.dot"));
            grafico.println(gc.graficar());
            grafico.close();

            String cmdDot = "C:\\Program Files\\Graphviz\\bin\\dot";
            String[] command = {cmdDot, "-Tpng", "arbol.dot", "-o", "arbol.png"};
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.start();
            view.appendTextOutput("Se ha guardado el AST en el archivo '/arbol.png'.\n");
        } catch (Exception e) {
            System.out.println(e);
            output.clear();
            view.appendTextOutput(e.getMessage());
            return;
        }

        File tree = new File("./arbol.png");
        try {
            Desktop.getDesktop().open(tree);
        } catch (IOException e) {
            view.appendTextOutput(e.getMessage());
        }
    }

    public void clean(){
        view.clearOutput();
        view.getCodeTextArea().setText("");
    }

    public void generateSymbolTableFile() {
        Hashtable<String, Simbolo> table = new Hashtable<>();
        String path = "./ts.txt";

        try {
            String code = view.getCodeTextArea().getText();
            MiLexico lexico = new MiLexico(new StringReader(code));
            Parser parser = new Parser(lexico);
            parser.parse();
            table = parser.action_obj.table;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FileWriter writer = new FileWriter(path);
            for (Simbolo symbol: table.values()
            ) {
                writer.write(symbol.toString() + "\n");
            }
            writer.close();
            // abrimos la tabla de símbolos una vez se escribió
            File file = new File(path);
            Desktop.getDesktop().open(file);
            view.appendTextOutput("Se ha guardado la tabla de símbolos en el archivo '/ts.txt'.\n");
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            view.appendTextOutput(e.getMessage());
        }


    }
}