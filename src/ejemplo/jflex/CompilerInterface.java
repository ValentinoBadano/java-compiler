package ejemplo.jflex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CompilerInterface extends JFrame {

    private JTextArea codeTextArea;
    private JButton compileButton;
    private JTextArea outputTextArea;

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
        compileButton = new JButton("Compilar");

        // área de texto para la salida
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setPreferredSize(new Dimension(600, 200));

        // menú
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem loadMenuItem = new JMenuItem("Cargar Archivo");
        loadMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Código a compilar:"), BorderLayout.NORTH);
        topPanel.add(codeScrollPane, BorderLayout.CENTER);
        topPanel.add(compileButton, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.CENTER);
        add(outputScrollPane, BorderLayout.SOUTH);
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

    public JButton getCompileButton() {
        return compileButton;
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
}
