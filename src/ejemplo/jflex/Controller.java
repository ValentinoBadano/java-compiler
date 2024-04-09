package ejemplo.jflex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;

public class Controller implements ActionListener {

    private final CompilerInterface view;

    public Controller(CompilerInterface view) {
        this.view = view;
    }

    public void init() {
        view.getCompileButton().addActionListener(this);
        view.getCleanButton().addActionListener(this);
        view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Compilar":
                this.compile();

                break;

            case "Borrar":
                this.clean();
                break;
        }
    }

    public void compile() {
        String code = view.getCodeTextArea().getText();
        MiLexico lexico = new MiLexico(new StringReader(code));
        view.clearOutput();

        while (true) {
            MiToken token = null;
            try {
                token = lexico.yylex();
            } catch (Error | IOException e) {
                view.appendTextOutput("ERROR: " + e.getMessage());
                return;
            }
            if (token == null)
                break;
            view.appendTextOutput("Token: " + token.toString() + "\n");
        }

        view.appendTextOutput("Compilación finalizada con éxito.");
    }

    public void clean(){
        view.clearOutput();
        view.getCodeTextArea().setText("");
    }
}