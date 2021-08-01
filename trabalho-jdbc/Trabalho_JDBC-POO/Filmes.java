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

public class Filmes extends WindowDatabase {
    JPanel FormFields[] = new JPanel[4];
    JTable Tabela;
    JTextArea InsNome, InsElenco, InsIndicacao, InsGenero, InsSinopse, AltID, AltNome, AltElenco, AltIndicacao,
            AltGenero, AltSinopse, ExcID;
    JButton BInserir, BAlterar, BExcluir;

    Filmes() {
        super("Filmes");
        CreateDataBank("BD-Filmes");
        try {
            SQLStatement.executeUpdate(
                    "CREATE TABLE FILMES (NOME VARCHAR(50), ELENCO VARCHAR(100), INDICACAO INT, GENERO VARCHAR(25), SINOPSE VARCHAR(100))");
        } catch (SQLException excecao) {
        }

        Border borda = BorderFactory.createLineBorder(Color.black);

        for (int i = 0; i < 4; i++)
            FormFields[i] = new JPanel();

        FormFields[0].setLayout(new GridLayout(6, 2));
        FormFields[0].add(new JLabel("Cadastrar filme"));
        BInserir = new JButton("Cadastrar");
        BInserir.addActionListener(new Insere());
        FormFields[0].add(BInserir);
        FormFields[0].add(new JLabel("Nome"));
        InsNome = new JTextArea();
        InsNome.setBorder(borda);
        FormFields[0].add(InsNome);
        FormFields[0].add(new JLabel("Elenco"));
        InsElenco = new JTextArea();
        InsElenco.setBorder(borda);
        FormFields[0].add(InsElenco);
        FormFields[0].add(new JLabel("Cassificação Indicativa"));
        InsIndicacao = new JTextArea();
        InsIndicacao.setBorder(borda);
        FormFields[0].add(InsIndicacao);
        FormFields[0].add(new JLabel("Genero"));
        InsGenero = new JTextArea();
        InsGenero.setBorder(borda);
        FormFields[0].add(InsGenero);
        FormFields[0].add(new JLabel("Sinopse"));
        InsSinopse = new JTextArea();
        InsSinopse.setBorder(borda);
        FormFields[0].add(InsSinopse);

        FormFields[1].setLayout(new GridLayout(7, 2));
        FormFields[1].add(new JLabel("Alterar filme"));
        BAlterar = new JButton("Alterar");
        BAlterar.addActionListener(new Altera());
        FormFields[1].add(BAlterar);
        FormFields[1].add(new JLabel("Nome do filme para alterar"));
        AltID = new JTextArea();
        AltID.setBorder(borda);
        FormFields[1].add(AltID);
        FormFields[1].add(new JLabel("Nome"));
        AltNome = new JTextArea();
        AltNome.setBorder(borda);
        FormFields[1].add(AltNome);
        FormFields[1].add(new JLabel("Elenco"));
        AltElenco = new JTextArea();
        AltElenco.setBorder(borda);
        FormFields[1].add(AltElenco);
        FormFields[1].add(new JLabel("Classificaçao Indicativa"));
        AltIndicacao = new JTextArea();
        AltIndicacao.setBorder(borda);
        FormFields[1].add(AltIndicacao);
        FormFields[1].add(new JLabel("Genero"));
        AltGenero = new JTextArea();
        AltGenero.setBorder(borda);
        FormFields[1].add(AltGenero);
        FormFields[1].add(new JLabel("Sinopse"));
        AltSinopse = new JTextArea();
        AltSinopse.setBorder(borda);
        FormFields[1].add(AltSinopse);

        FormFields[2].setLayout(new GridLayout(2, 2));
        FormFields[2].add(new JLabel("Excluir filme"));
        BExcluir = new JButton("Excluir");
        BExcluir.addActionListener(new Exclui());
        FormFields[2].add(BExcluir);
        FormFields[2].add(new JLabel("Nome do filme a ser apagado"));
        ExcID = new JTextArea();
        ExcID.setBorder(borda);
        FormFields[2].add(ExcID);

        Tabela = new JTable(new Object[1][6], new String[] { "Nome", "Elenco", "Indicacao", "Genero", "Sinopse" });
        FormFields[3].setLayout(new GridLayout(2, 1));
        FormFields[3].add(Tabela.getTableHeader());
        FormFields[3].add(Tabela);

        for (int i = 0; i < 4; i++)
            FormFields[i].setBorder(borda);
        AtualizaJanela();
        CriaJanela(FormFields);
    }

    public void AtualizaJanela() {
        DefaultTableModel Modelo = new DefaultTableModel(
                new Object[] { "Nome", "Elenco ", "Indicacao", "Genero", "Sinopse" }, 0);
        try {
            ResultSet Dados = SQLStatement.executeQuery("SELECT * FROM FILMES");
            while (Dados.next()) {
                String Nome = Dados.getString("NOME");
                String Elenco = Dados.getString("ELENCO");
                int Indicacao = Dados.getInt("Indicacao");
                String Genero = Dados.getString("GENERO");
                String Sinopse = Dados.getString("SINOPSE");
                Modelo.addRow(new Object[] { Nome, Elenco, Indicacao, Genero, Sinopse });
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
            String Nome = InsNome.getText();
            String Elenco = InsElenco.getText();
            String indicacao = InsIndicacao.getText();
            String Genero = InsGenero.getText();
            String Sinopse = InsSinopse.getText();

            try {
                SQLStatement.executeUpdate("INSERT INTO FILMES VALUES('" + Nome + "', '" + Elenco + "', '" + indicacao
                        + "', '" + Genero + "', '" + Sinopse + "')");
            } catch (SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            AtualizaJanela();
        }
    }

    class Altera implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String NomeEspecificado = AltID.getText();
            String Nome = AltNome.getText();
            String Elenco = AltElenco.getText();
            String Indicacao = AltIndicacao.getText();
            String Genero = AltGenero.getText();
            String Sinopse = AltSinopse.getText();

            try {
                SQLStatement.executeUpdate("UPDATE FILMES SET NOME='" + Nome + "', ELENCO='" + Elenco + "', INDICACAO='"
                        + Indicacao + "', GENERO='" + Genero + "', SINOPSE='" + Sinopse + "' WHERE NOME='"
                        + NomeEspecificado + "'");
            } catch (SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            AtualizaJanela();
        }
    }

    class Exclui implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String Nome = ExcID.getText();
            if (Nome.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insira um nome para especificar o filme que deseja excluir.");
                return;
            }
            try {
                SQLStatement.executeUpdate("DELETE FROM FILMES WHERE NOME='" + Nome + "'");
            } catch (SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            AtualizaJanela();
        }
    }
}