import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.SQLException;
import java.sql.ResultSet;

public class Filmes extends WindowDatabase {
    JPanel FormFields[] = new JPanel[4];
    JTable MoviesTable;
    JTextArea CreateName, CreateCast, CreateParentalRating, CreateMovieGenre, CreateSynopsis, UpdatableName, UpdateName, UpdateCast, UpdateParentalRating,
            UpdateMovieGenre, UpdateSynopsis, DeletableName;
    JButton CreateBtn, UpdateBtn, DeleteBtn;

    Filmes() {
        super("Filmes");
        CreateDataBank("BD-Filmes");
        try {
            SQLStatement.executeUpdate(
                    "CREATE TABLE FILMES (NOME VARCHAR(50), ELENCO VARCHAR(100), INDICACAO INT, GENERO VARCHAR(25), SINOPSE VARCHAR(100))");
        } catch (SQLException excecao) {
        }

        Border borderBox = BorderFactory.createLineBorder(Color.blue);

        for (int i = 0; i < 4; i++)
            FormFields[i] = new JPanel();

        FormFields[0].setLayout(new GridLayout(6, 2));
        FormFields[0].add(new JLabel("Cadastrar filme"));
        CreateBtn = new JButton("Cadastrar");
        CreateBtn.addActionListener(new CreateNew());
        FormFields[0].add(CreateBtn);
        FormFields[0].add(new JLabel("Nome"));
        CreateName = new JTextArea();
        CreateName.setBorder(borderBox);
        FormFields[0].add(CreateName);
        FormFields[0].add(new JLabel("Elenco"));
        CreateCast = new JTextArea();
        CreateCast.setBorder(borderBox);
        FormFields[0].add(CreateCast);
        FormFields[0].add(new JLabel("Cassificação Indicativa"));
        CreateParentalRating = new JTextArea();
        CreateParentalRating.setBorder(borderBox);
        FormFields[0].add(CreateParentalRating);
        FormFields[0].add(new JLabel("Genero"));
        CreateMovieGenre = new JTextArea();
        CreateMovieGenre.setBorder(borderBox);
        FormFields[0].add(CreateMovieGenre);
        FormFields[0].add(new JLabel("Sinopse"));
        CreateSynopsis = new JTextArea();
        CreateSynopsis.setBorder(borderBox);
        FormFields[0].add(CreateSynopsis);

        FormFields[1].setLayout(new GridLayout(7, 2));
        FormFields[1].add(new JLabel("Alterar filme"));
        UpdateBtn = new JButton("Alterar");
        UpdateBtn.addActionListener(new UpdateFields());
        FormFields[1].add(UpdateBtn);
        FormFields[1].add(new JLabel("Nome do filme para alterar"));
        UpdatableName = new JTextArea();
        UpdatableName.setBorder(borderBox);
        FormFields[1].add(UpdatableName);
        FormFields[1].add(new JLabel("Nome"));
        UpdateName = new JTextArea();
        UpdateName.setBorder(borderBox);
        FormFields[1].add(UpdateName);
        FormFields[1].add(new JLabel("Elenco"));
        UpdateCast = new JTextArea();
        UpdateCast.setBorder(borderBox);
        FormFields[1].add(UpdateCast);
        FormFields[1].add(new JLabel("Classificaçao Indicativa"));
        UpdateParentalRating = new JTextArea();
        UpdateParentalRating.setBorder(borderBox);
        FormFields[1].add(UpdateParentalRating);
        FormFields[1].add(new JLabel("Genero"));
        UpdateMovieGenre = new JTextArea();
        UpdateMovieGenre.setBorder(borderBox);
        FormFields[1].add(UpdateMovieGenre);
        FormFields[1].add(new JLabel("Sinopse"));
        UpdateSynopsis = new JTextArea();
        UpdateSynopsis.setBorder(borderBox);
        FormFields[1].add(UpdateSynopsis);

        FormFields[2].setLayout(new GridLayout(2, 2));
        DeleteBtn = new JButton("Excluir (digite o nome)");
        DeleteBtn.addActionListener(new DeleteData());
        FormFields[2].add(DeleteBtn);
        DeletableName = new JTextArea();
        DeletableName.setBorder(borderBox);
        FormFields[2].add(DeletableName);

        MoviesTable = new JTable(new Object[1][6], new String[] { "Nome", "Elenco", "Indicacao", "Genero", "Sinopse" });
        FormFields[3].setLayout(new GridLayout(2, 1));
        FormFields[3].add(MoviesTable.getTableHeader());
        FormFields[3].add(MoviesTable);

        for (int i = 0; i < 4; i++)
            FormFields[i].setBorder(borderBox);
        Update();
        CreateUserInterface(FormFields);
    }

    public void Update() {
        DefaultTableModel ModeloFilmes = new DefaultTableModel(
                new Object[] { "Nome", "Elenco ", "Indicacao", "Genero", "Sinopse" }, 0);
        try {
            ResultSet SQLResult = SQLStatement.executeQuery("SELECT * FROM FILMES");
            while (SQLResult.next()) {
                String Nome = SQLResult.getString("NOME");
                String Elenco = SQLResult.getString("ELENCO");
                int Indicacao = SQLResult.getInt("Indicacao");
                String Genero = SQLResult.getString("GENERO");
                String Sinopse = SQLResult.getString("SINOPSE");
                ModeloFilmes.addRow(new Object[] { Nome, Elenco, Indicacao, Genero, Sinopse });
                SQLResult.next();
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
            System.exit(1);
        }
        MoviesTable.setModel(ModeloFilmes);
    }

    class CreateNew implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String Nome = CreateName.getText();
            String Elenco = CreateCast.getText();
            String indicacao = CreateParentalRating.getText();
            String Genero = CreateMovieGenre.getText();
            String Sinopse = CreateSynopsis.getText();

            try {
                SQLStatement.executeUpdate("INSERT INTO FILMES VALUES('" + Nome + "', '" + Elenco + "', '" + indicacao
                        + "', '" + Genero + "', '" + Sinopse + "')");
            } catch (SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }

    class UpdateFields implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String NomeEspecificado = UpdatableName.getText();
            String Nome = UpdateName.getText();
            String Elenco = UpdateCast.getText();
            String Indicacao = UpdateParentalRating.getText();
            String Genero = UpdateMovieGenre.getText();
            String Sinopse = UpdateSynopsis.getText();

            try {
                SQLStatement.executeUpdate("UPDATE FILMES SET NOME='" + Nome + "', ELENCO='" + Elenco + "', INDICACAO='"
                        + Indicacao + "', GENERO='" + Genero + "', SINOPSE='" + Sinopse + "' WHERE NOME='"
                        + NomeEspecificado + "'");
            } catch (SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }

    class DeleteData implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String Nome = DeletableName.getText();
      
            try {
                SQLStatement.executeUpdate("DELETE FROM FILMES WHERE NOME='" + Nome + "'");
            } catch (SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }
}