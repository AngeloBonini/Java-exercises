import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.SQLException;
import java.sql.ResultSet;

public class Musicas extends Janela {
    JPanel Campos[] = new JPanel[4];
    JTable Tabela;
    JTextArea InsTitulo, InsDuracao, InsLetra, AltID, AltTitulo, AltDuracao, AltLetra, ExcID;
    JButton BInserir, BAlterar, BExcluir;

    Musicas() {
        super("Musicas");
        CriaBancoDados("BD-Musicas");
        try {
            Afirmacao.executeUpdate("CREATE TABLE MUSICAS (TITULO VARCHAR(50), DURACAO FLOAT, LETRA VARCHAR(500))");
        } catch (SQLException excecao) {}

        Border borda = BorderFactory.createLineBorder(Color.black);

        for (int i = 0; i < 4; i++) Campos[i] = new JPanel();

        Campos[0].setLayout(new GridLayout(4, 2));
        Campos[0].add(new JLabel("Cadastrar musica"));
        BInserir = new JButton("Cadastrar");
        BInserir.addActionListener(new Insere());
        Campos[0].add(BInserir);
        Campos[0].add(new JLabel("Título"));
        InsTitulo = new JTextArea();
        InsTitulo.setBorder(borda);
        Campos[0].add(InsTitulo);
        Campos[0].add(new JLabel("Duração"));
        InsDuracao = new JTextArea();
        InsDuracao.setBorder(borda);
        Campos[0].add(InsDuracao);
        Campos[0].add(new JLabel("Letra"));
        InsLetra = new JTextArea();
        InsLetra.setBorder(borda);
        Campos[0].add(InsLetra);

        Campos[1].setLayout(new GridLayout(5, 2));
        Campos[1].add(new JLabel("Alterar dados da música"));
        BAlterar = new JButton("Alterar");
        BAlterar.addActionListener(new Altera());
        Campos[1].add(BAlterar);
        Campos[1].add(new JLabel("Título da música a ser alterada"));
        AltID = new JTextArea();
        AltID.setBorder(borda);
        Campos[1].add(AltID);
        Campos[1].add(new JLabel("Novo Título"));
        AltTitulo = new JTextArea();
        AltTitulo.setBorder(borda);
        Campos[1].add(AltTitulo);
        Campos[1].add(new JLabel("Nova duração"));
        AltDuracao = new JTextArea();
        AltDuracao.setBorder(borda);
        Campos[1].add(AltDuracao);
        Campos[1].add(new JLabel("Nova Letra"));
        AltLetra = new JTextArea();
        AltLetra.setBorder(borda);
        Campos[1].add(AltLetra);

        Campos[2].setLayout(new GridLayout(2, 2));
        Campos[2].add(new JLabel("Excluir Excluir música"));
        BExcluir = new JButton("Excluir");
        BExcluir.addActionListener(new Exclui());
        Campos[2].add(BExcluir);
        Campos[2].add(new JLabel("Nome do nome da música excluida"));
        ExcID = new JTextArea();
        ExcID.setBorder(borda);
        Campos[2].add(ExcID);

        Tabela = new JTable(new Object[1][4], new String[] {"Titulo", "Duração", "Letra"});
        Campos[3].setLayout(new GridLayout(2, 1));
        Campos[3].add(Tabela.getTableHeader());
        Campos[3].add(Tabela);

        for (int i = 0; i < 4; i++) Campos[i].setBorder(borda);
        Update();
        CriaJanela(Campos);
    }
    public void Update() {
        DefaultTableModel Modelo = new DefaultTableModel(new Object[] {"Título", "Duração", "Letra"}, 0);
        try {
            ResultSet Dados = Afirmacao.executeQuery("SELECT * FROM MUSICAS");
            while (Dados.next()) {
                String Titulo = Dados.getString("TITULO");
                float Duracao = Dados.getFloat("DURACAO");
                String Letra = Dados.getString("LETRA");
                Modelo.addRow(new Object[] {Titulo, Duracao, Letra});
                Dados.next();
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
            System.exit(1);
        }
        Tabela.setModel(Modelo);
    }
    class Insere implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String Titulo = InsTitulo.getText();
            String Duracao = InsDuracao.getText();
            String Letra = InsLetra.getText();
   
            try {
                Afirmacao.executeUpdate("INSERT INTO MUSICAS VALUES('"+ Titulo +"', '"+ Duracao +"', '"+ Letra +"')");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }
    class Altera implements ActionListener {
        
        public void actionPerformed(ActionEvent evento) {
            String ClienteEspecificado = AltID.getText();
            String Titulo = AltTitulo.getText();
            String Duracao = AltDuracao.getText();
            String Letra = AltLetra.getText();
   
            try {
                Afirmacao.executeUpdate("UPDATE MUSICAS SET TITULO='"+ Titulo +"', DURACAO='"+ Duracao +"', LETRA='"+ Letra +"' WHERE TITULO='"+ ClienteEspecificado +"'");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }
    class Exclui implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String Titulo = ExcID.getText();
    
            try {
                Afirmacao.executeUpdate("DELETE FROM MUSICAS WHERE TITULO='"+ Titulo +"'");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }
}