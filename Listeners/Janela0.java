public class JanelaO extends JFrame {
    JPanel Pbuttons = new JPanel(new GridLayout(0, 1));
    JPanel Pleft = new JPanel(new BorderLayout());
    JTextArea textArea;
    JButton open = new JButton("Abrir");
    JButton save = new JButton("Salvar");
    JButton saveas = new JButton("Salvar como");
    JButton close = new JButton("Fechar");
    StringBuffer txt;
    String nomeArquivo;

    // Classe - Abrir Arquivo
    class Openfile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            nomeArquivo = JOptionPane.showInputDialog(null, "Digite o nome do arquivo: ");
            try {
                FileInputStream file = new FileInputStream(nomeArquivo);
                Scanner in = new Scanner(file);
                StringBuffer txt = new StringBuffer();
                while (in.hasNextLine()) {
                    txt.append(in.nextLine());
                    txt.append("\n");
                }
                in.close();
                file.close();
                textArea.setText(txt.toString());
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Arquivo não foi encontrado!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Arquivo não foi encerrado!");
            }
        }
    }

    // Função - Somente Salva
    public void savefile() {
        String text = textArea.getText();
        try {
            FileOutputStream file = new FileOutputStream(nomeArquivo);
            file.write(text.getBytes());
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não foi criado!");
        } catch (IOException ex) {
            System.out.println("Arquivo não foi fechado!");
        }

    }

    // Classe - Salvar Arquivo
    class Savefile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Caso em algum momento não tenha essa informação,
            // pega o nome do arquivo novamente
            if (nomeArquivo == null) {
                nomeArquivo = JOptionPane.showInputDialog(null, "Digite o nome do arquivo:");
            }
            savefile();
        }

    }

    // Classe - Salvar Arquivo Como
    class Saveasfile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            nomeArquivo = JOptionPane.showInputDialog(null, "Digite o nome do arquivo:");
            savefile();
        }
    }

    // Classe - Fechar
    class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    JanelaO() {
        super("Editor de Texto");
        open.addActionListener(new Openfile());
        save.addActionListener(new Savefile());
        saveas.addActionListener(new Saveasfile());
        close.addActionListener(new Close());
        Pbuttons.add(open);
        Pbuttons.add(save);
        Pbuttons.add(saveas);
        Pbuttons.add(close);
        Pleft.add(Pbuttons, BorderLayout.NORTH);
        add(Pleft, BorderLayout.WEST);
        add(textArea = new JTextArea(10, 30), BorderLayout.CENTER);
    }
}