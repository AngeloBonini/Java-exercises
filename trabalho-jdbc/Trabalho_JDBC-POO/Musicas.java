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

public class Musicas extends WindowDatabase {
    JPanel FormFields[] = new JPanel[4];
    JTable Tabela;
    JTextArea InsTitulo, InsDuracao, InsLetra, AltID, AltTitulo, AltDuracao, AltLetra, ExcID;
    JButton BInserir, BAlterar, BExcluir;

    Musicas() {
        super("Musicas");
        CreateDataBank("BD-Musicas");
        try {
            SQLStatement.executeUpdate("CREATE TABLE MUSICAS (TITULO VARCHAR(50), DURACAO FLOAT, LETRA VARCHAR(500))");
        } catch (SQLException excecao) {}

        Border borda = BorderFactory.createLineBorder(Color.black);

        for (int i = 0; i < 4; i++) FormFields[i] = new JPanel();

        FormFields[0].setLayout(new GridLayout(4, 2));
        FormFields[0].add(new JLabel("Cadastrar musica"));
        BInserir = new JButton("Cadastrar");
        BInserir.addActionListener(new Insere());
        FormFields[0].add(BInserir);
        FormFields[0].add(new JLabel("Título"));
        InsTitulo = new JTextArea();
        InsTitulo.setBorder(borda);
        FormFields[0].add(InsTitulo);
        FormFields[0].add(new JLabel("Duração"));
        InsDuracao = new JTextArea();
        InsDuracao.setBorder(borda);
        FormFields[0].add(InsDuracao);
        FormFields[0].add(new JLabel("Letra"));
        InsLetra = new JTextArea();
        InsLetra.setBorder(borda);
        FormFields[0].add(InsLetra);

        FormFields[1].setLayout(new GridLayout(5, 2));
        FormFields[1].add(new JLabel("Alterar dados da música"));
        BAlterar = new JButton("Alterar");
        BAlterar.addActionListener(new Altera());
        FormFields[1].add(BAlterar);
        FormFields[1].add(new JLabel("Título da música a ser alterada"));
        AltID = new JTextArea();
        AltID.setBorder(borda);
        FormFields[1].add(AltID);
        FormFields[1].add(new JLabel("Novo Título"));
        AltTitulo = new JTextArea();
        AltTitulo.setBorder(borda);
        FormFields[1].add(AltTitulo);
        FormFields[1].add(new JLabel("Nova duração"));
        AltDuracao = new JTextArea();
        AltDuracao.setBorder(borda);
        FormFields[1].add(AltDuracao);
        FormFields[1].add(new JLabel("Nova Letra"));
        AltLetra = new JTextArea();
        AltLetra.setBorder(borda);
        FormFields[1].add(AltLetra);

        FormFields[2].setLayout(new GridLayout(2, 2));
        FormFields[2].add(new JLabel("Excluir Excluir música"));
        BExcluir = new JButton("Excluir");
        BExcluir.addActionListener(new Exclui());
        FormFields[2].add(BExcluir);
        FormFields[2].add(new JLabel("Nome do nome da música excluida"));
        ExcID = new JTextArea();
        ExcID.setBorder(borda);
        FormFields[2].add(ExcID);

        Tabela = new JTable(new Object[1][4], new String[] {"Titulo", "Duração", "Letra"});
        FormFields[3].setLayout(new GridLayout(2, 1));
        FormFields[3].add(Tabela.getTableHeader());
        FormFields[3].add(Tabela);

        for (int i = 0; i < 4; i++) FormFields[i].setBorder(borda);
        Update();
        CriaJanela(FormFields);
    }
    public void Update() {
        DefaultTableModel Modelo = new DefaultTableModel(new Object[] {"Título", "Duração", "Letra"}, 0);
        try {
            ResultSet Dados = SQLStatement.executeQuery("SELECT * FROM MUSICAS");
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
                SQLStatement.executeUpdate("INSERT INTO MUSICAS VALUES('"+ Titulo +"', '"+ Duracao +"', '"+ Letra +"')");
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
                SQLStatement.executeUpdate("UPDATE MUSICAS SET TITULO='"+ Titulo +"', DURACAO='"+ Duracao +"', LETRA='"+ Letra +"' WHERE TITULO='"+ ClienteEspecificado +"'");
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
                SQLStatement.executeUpdate("DELETE FROM MUSICAS WHERE TITULO='"+ Titulo +"'");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }
}