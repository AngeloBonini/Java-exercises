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
    JTextArea CreateTitle, CreateDuration, CreateLyrics, UpdatableName, UpdateTitle, UpdateDuration, UpdateLyrics,
            DeletableName;
    JButton CreateBtn, UpdateBtn, DeleteBtn;

    Musicas() {
        super("Musicas");
        CreateDataBank("BD-Musicas");
        try {
            SQLStatement.executeUpdate("CREATE TABLE MUSICAS (TITULO VARCHAR(50), DURACAO FLOAT, LETRA VARCHAR(500))");
        } catch (SQLException excecao) {
        }

        Border borda = BorderFactory.createLineBorder(Color.black);

        for (int i = 0; i < 4; i++)
            FormFields[i] = new JPanel();

        FormFields[0].setLayout(new GridLayout(4, 2));

        CreateBtn = new JButton("Cadastrar");
        CreateBtn.addActionListener(new CreateNew());
        FormFields[0].add(CreateBtn);
        FormFields[0].add(new JLabel("Título"));
        CreateTitle = new JTextArea();
        CreateTitle.setBorder(borda);
        FormFields[0].add(CreateTitle);
        FormFields[0].add(new JLabel("Duração"));
        CreateDuration = new JTextArea();
        CreateDuration.setBorder(borda);
        FormFields[0].add(CreateDuration);
        FormFields[0].add(new JLabel("Letra"));
        CreateLyrics = new JTextArea();
        CreateLyrics.setBorder(borda);
        FormFields[0].add(CreateLyrics);

        FormFields[1].setLayout(new GridLayout(5, 2));

        JButton UpdateBtn = new JButton("Alterar");
        UpdateBtn.addActionListener(new UpdateFields());
        FormFields[1].add(UpdateBtn);
        FormFields[1].add(new JLabel("Título da música a ser alterada"));
        UpdatableName = new JTextArea();
        UpdatableName.setBorder(borda);
        FormFields[1].add(UpdatableName);
        FormFields[1].add(new JLabel("Novo Título"));
        UpdateTitle = new JTextArea();
        UpdateTitle.setBorder(borda);
        FormFields[1].add(UpdateTitle);
        FormFields[1].add(new JLabel("Nova duração"));
        UpdateDuration = new JTextArea();
        UpdateDuration.setBorder(borda);
        FormFields[1].add(UpdateDuration);
        FormFields[1].add(new JLabel("Nova Letra"));
        UpdateLyrics = new JTextArea();
        UpdateLyrics.setBorder(borda);
        FormFields[1].add(UpdateLyrics);

        FormFields[2].setLayout(new GridLayout(2, 2));

        DeleteBtn = new JButton("Excluir");
        DeleteBtn.addActionListener(new Deletedata());
        FormFields[2].add(DeleteBtn);
        DeletableName = new JTextArea();
        DeletableName.setBorder(borda);
        FormFields[2].add(DeletableName);

        Tabela = new JTable(new Object[1][4], new String[] { "Titulo", "Duração", "Letra" });
        FormFields[3].setLayout(new GridLayout(2, 1));
        FormFields[3].add(Tabela.getTableHeader());
        FormFields[3].add(Tabela);

        for (int i = 0; i < 4; i++)
            FormFields[i].setBorder(borda);
        Update();
        CriaJanela(FormFields);
    }

    class CreateNew implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String Titulo = CreateTitle.getText();
            String Duracao = CreateDuration.getText();
            String Letra = CreateLyrics.getText();

            try {
                SQLStatement.executeUpdate(
                        "INSERT INTO MUSICAS VALUES('" + Titulo + "', '" + Duracao + "', '" + Letra + "')");
            } catch (SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }

    class UpdateFields implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            String musicaAtualizada = UpdatableName.getText();
            String Titulo = UpdateTitle.getText();
            String Duracao = UpdateDuration.getText();
            String Letra = UpdateLyrics.getText();

            try {
                SQLStatement.executeUpdate("UPDATE MUSICAS SET TITULO='" + Titulo + "', DURACAO='" + Duracao
                        + "', LETRA='" + Letra + "' WHERE TITULO='" + musicaAtualizada + "'");
            } catch (SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }

    class Deletedata implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String Titulo = DeletableName.getText();

            try {
                SQLStatement.executeUpdate("DELETE FROM MUSICAS WHERE TITULO='" + Titulo + "'");
            } catch (SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }

    public void Update() {
        DefaultTableModel Modelo = new DefaultTableModel(new Object[] { "Título", "Duração", "Letra" }, 0);
        try {
            ResultSet Dados = SQLStatement.executeQuery("SELECT * FROM MUSICAS");
            while (Dados.next()) {
                String Titulo = Dados.getString("TITULO");
                float Duracao = Dados.getFloat("DURACAO");
                String Letra = Dados.getString("LETRA");
                Modelo.addRow(new Object[] { Titulo, Duracao, Letra });
                Dados.next();
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
            System.exit(1);
        }
        Tabela.setModel(Modelo);
    }
}