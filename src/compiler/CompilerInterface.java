package compiler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CompilerInterface extends JFrame {

    private JTextArea codeTextArea;
    private JButton lexicButton;
    private JButton syntacticButton;
    private JTextArea outputTextArea;
    private JButton cleanButton;

    public CompilerInterface() {
        setTitle("Compilador");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // área de texto para el código
        codeTextArea = new JTextArea();
        codeTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        JScrollPane codeScrollPane = new JScrollPane(codeTextArea);

        // botón de compilación
        lexicButton = new JButton("Análisis Léxico");
        this.lexicButton.setActionCommand("lexico");

        // boton de limpiar
        cleanButton = new JButton("Borrar");

        syntacticButton = new JButton("Análisis Sintáctico"); //
        this.syntacticButton.setActionCommand("syntactic");

        // área de texto para la salida
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setPreferredSize(new Dimension(600, 250));

        // menú
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenu helpMenu = new JMenu("Ayuda");
        JMenu toolsMenu = new JMenu("Herramientas");

        JMenuItem loadMenuItem = new JMenuItem("Cargar Archivo");
        JMenuItem symbolTableItem = new JMenuItem("Generar tabla de símbolos");
        JMenuItem instructionsItem = new JMenuItem("Instrucciones");

        instructionsItem.addActionListener(e -> showHelpFile());
        loadMenuItem.addActionListener(e -> loadFile());

        fileMenu.add(loadMenuItem);
        toolsMenu.add(symbolTableItem);
        helpMenu.add(instructionsItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonsPanel = new JPanel();
        topPanel.add(new JLabel("Código a compilar:"), BorderLayout.NORTH);
        topPanel.add(codeScrollPane, BorderLayout.CENTER);
//        topPanel.add(compileButton, BorderLayout.SOUTH);
        topPanel.add(cleanButton, BorderLayout.SOUTH);
        buttonsPanel.add(lexicButton);
        buttonsPanel.add(syntacticButton);
        buttonsPanel.add(cleanButton);
        topPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.CENTER);
        add(outputScrollPane, BorderLayout.SOUTH);
    }

    private void showHelpFile() {
        try {
            // Replace "help.html" with the actual file name and path
            File helpFile = new File("src/help.txt");
            Desktop.getDesktop().open(helpFile);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al abrir el archivo de ayuda.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                codeTextArea.setText(content.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JTextArea getCodeTextArea() {
        return codeTextArea;
    }

    public JButton getLexicButton() {
        return lexicButton;
    }

    public JTextArea getOutputTextArea() {
        return outputTextArea;
    }

    public void appendTextOutput(String text) {
        this.getOutputTextArea().append(text);
    }

    public void clearOutput() {
        this.getOutputTextArea().setText("");
    }

    public JButton getCleanButton(){
        return cleanButton;
    }

    public JButton getSyntacticButton() {
        return syntacticButton;
    }
}