package compiler;

import java_cup.runtime.Symbol;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        view.getSymbolTableItem().addActionListener(this);
        view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "lexico" -> this.doLexicalAnalysis();
            case "syntactic" -> this.doSyntacticAnalysis();
            case "table" -> this.generateSymbolTableFile();
            case "Borrar" -> this.clean();
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
        }

        for (String line: output
             ) {
            view.appendTextOutput(line + "\n");
        }


    }

    public void clean(){
        view.clearOutput();
        view.getCodeTextArea().setText("");
    }

    public void generateSymbolTableFile() {
        Hashtable<String, Simbolo> table = new Hashtable<>();
        String path = "src/ts.txt";

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
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }


    }
}