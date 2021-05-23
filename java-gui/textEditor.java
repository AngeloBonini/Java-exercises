import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;

public class textEditor extends JFrame {

    textEditor() {
        super("textEditor");

        JPanel editorPanel = new JPanel(new GridLayout(10, 3));

        editorPanel.add(new JButton("Abrir"));
        editorPanel.add(new JButton("Salvar"));
        editorPanel.add(new JButton("Salvar Como"));
        editorPanel.add(new JButton("Fechar"));
        add(editorPanel, BorderLayout.CENTER);
        add(new JTextArea(10, 40), BorderLayout.EAST);

        pack();
        setVisible(true);
    }

    static public void main(String[] args) {
        new textEditor();
    }
}
