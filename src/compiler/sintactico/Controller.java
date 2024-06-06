package compiler.sintactico;

import compiler.CompilerInterface;
import compiler.simbolo.*;
import compiler.lexico.*;
import compiler.ast.*;
import java_cup.runtime.Symbol;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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
        view.getExecuteButton().addActionListener(this);
        view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "lexico":this.doLexicalAnalysis(); break;
            case "syntactic": this.doSyntacticAnalysis(); break;
            case "ast":this.doAstAnalysis();break;
            case "llvm":this.doCompilation();break;
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
        TablaSimbolos.reset();


        try {
            String code = view.getCodeTextArea().getText();
            MiLexico lexico = new MiLexico(new StringReader(code));
            Parser parser = new Parser(lexico);
            parser.parse();
            output = parser.action_obj.output;

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
        TablaSimbolos.reset();

        List<String> output = new ArrayList<>();

        try {
            String code = view.getCodeTextArea().getText();
            MiLexico lexico = new MiLexico(new StringReader(code));
            Parser parser = new Parser(lexico);
            final GeneracionCodigo gc = (GeneracionCodigo) parser.parse().value;

            parser.parse();
            output = parser.action_obj.output;

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
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
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

    private void doCompilation() {
        System.out.println("Compilando..");
        TablaSimbolos.reset(); view.clearOutput();
        GeneracionCodigo gc = null;

        try {
            String code = view.getCodeTextArea().getText();
            MiLexico lexico = new MiLexico(new StringReader(code));
            final Parser sintactico = new Parser(lexico);
            gc = (GeneracionCodigo) sintactico.parse().value;
        } catch (Error | Exception e) {
            view.appendTextOutput("Error al parsear el código\n");
            view.appendTextOutput(e.getMessage());
        }


        try {
            //generar codigo IR para el LLVM
            PrintWriter pw = new PrintWriter(new FileWriter("programa.ll"));
            assert gc != null : "Algo muy malo ocurrió al parsear el código";
            pw.println(gc.generarCodigo());
            pw.close();
            System.out.println("Código generado");
            view.appendTextOutput("Código generado y guardado en /programa.ll\n");
        } catch (Exception e){
            System.out.println("Error al generar el código IR");
            view.appendTextOutput(e.toString()); return;
        }

        try {
            // genera el archivo objeto -> programa.o
            Process process = Runtime.getRuntime().exec("clang -c -o programa.o programa.ll");
            readProcess(process);
            System.out.println("Archivo objeto generado");
        } catch (Exception e){
            System.out.println("Error al generar el archivo objeto");
            view.appendTextOutput(e.getMessage()); return;
        }

        try {
            Process process2 = Runtime.getRuntime().exec("gcc -o programa.exe programa.o");
            readProcess(process2);
            System.out.println("Ejecutable generado");
            executeProgram();
        } catch (Exception e) {
            System.out.println("Error con el clang");
            view.appendTextOutput(e.getMessage());
        }


    }

    private void executeProgram() throws InterruptedException, IOException {
            String command = "cmd /c start cmd.exe /k \"programa.exe\"";
            Process process = Runtime.getRuntime().exec(command);
            readProcess(process);
            int exitCode = process.waitFor();
            System.out.println("Proceso terminado con código " + exitCode);
    }

    private void readProcess(Process process2) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process2.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process2.getErrorStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            view.appendTextOutput(line  + "\n");
        }
        while ((line = errorReader.readLine()) != null) {
            System.err.println(line);
            view.appendTextOutput(line  + "\n");
        }
    }

    public void clean(){
        view.clearOutput();
        view.getCodeTextArea().setText("");
    }

    public void generateSymbolTableFile() {
        TablaSimbolos.reset();
        Hashtable<String, Simbolo> table = TablaSimbolos.ts;
        String path = "./ts.txt";

        try {
            String code = view.getCodeTextArea().getText();
            MiLexico lexico = new MiLexico(new StringReader(code));
            Parser parser = new Parser(lexico);
            parser.parse();

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