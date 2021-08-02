import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;

public class textEditor extends JFrame {
    JTextArea textArea;
    JButton open = new JButton("Abrir");
    JButton save = new JButton("Salvar");
    JButton saveas = new JButton("Salvar como");
    JButton close = new JButton("Fechar");
    StringBuffer txt;
    String fileName;

    class Abrir implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileName = JOptionPane.showInputDialog(null, "nome do arquivo: ");
            try {
                FileInputStream arquivo = new FileInputStream(fileName);
                Scanner identificadorArquivo = new Scanner(arquivo);
                StringBuffer txt = new StringBuffer();
                while (identificadorArquivo.hasNextLine()) {
                    txt.append(identificadorArquivo.nextLine());
                    txt.append("\n");
                }
                identificadorArquivo.close();
                arquivo.close();
                textArea.setText(txt.toString());
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Arquivo não  encontrado");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Arquivo não  encerrado");
            }
        }
    }

    public void salvar() {
        String text = textArea.getText();
        try {
            FileOutputStream arquivo = new FileOutputStream(nomeArquivo);
            arquivo.write(text.getBytes());
            arquivo.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não foi criado!");
        } catch (IOException ex) {
            System.out.println("Arquivo não foi fechado!");
        }

    }

    class SalvarComo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileName = JOptionPane.showInputDialog(null, "Digite o nome do arquivo:");
            salvar();
        }
    }

    class Fechar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    textEditor() {
        super("textEditor");

        JPanel editorPanel = new JPanel(new GridLayout(10, 3));
        open.addActionListener(new Openfile());
        save.addActionListener(new Savefile());
        saveas.addActionListener(new Saveasfile());
        close.addActionListener(new Close());
        editorPanel.add(open);
        editorPanel.add(save);
        editorPanel.add(saveas);
        editorPanel.add(close);
        add(editorPanel, BorderLayout.CENTER);
        add(new JTextArea(10, 40), BorderLayout.EAST);

        pack();
        setVisible(true);
    }

    static public void main(String[] args) {
        new textEditor();
    }
}
