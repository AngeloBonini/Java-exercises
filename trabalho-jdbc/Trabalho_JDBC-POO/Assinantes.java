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

public class Assinantes extends WindowDatabase {
    JPanel FormFields[] = new JPanel[4];
    JTable TabelaAssinantes;
    JTextArea CreateName, CreateCreditCard, CreateAddress, CreateSignaturePlain, UpdatableName, UpdatateCreditCard,
            UpdateName, UpdateAddress, UpdateSignaturePlain, DeletableName;
    JButton CreateBtn, UpdateBtn, DeleteBtn;

    Assinantes() {
        super("Assinantes");
        CreateDataBank("BD-Assinantes");
        try {
            SQLStatement.executeUpdate(
                    "CREATE TABLE ASSINANTES ( NOME VARCHAR(50), ENDERECO VARCHAR(25),  CARTAO VARCHAR(25), PLANO VARCHAR(50))");
        } catch (SQLException excecao) {
        }
        Border borderBox = BorderFactory.createLineBorder(Color.green);

        for (int i = 0; i < 4; i++)
            FormFields[i] = new JPanel();

        FormFields[0].setLayout(new GridLayout(5, 2));
        FormFields[0].add(new JLabel("Cadastrar assinante"));
        CreateBtn = new JButton("Cadastrar");
        CreateBtn.addActionListener(new CreateNew());
        FormFields[0].add(CreateBtn);
        FormFields[0].add(new JLabel("Cartao"));
        CreateCreditCard = new JTextArea();
        CreateCreditCard.setBorder(borderBox);
        FormFields[0].add(CreateCreditCard);
        FormFields[0].add(new JLabel("Nome"));
        CreateName = new JTextArea();
        CreateName.setBorder(borderBox);
        FormFields[0].add(CreateName);
        FormFields[0].add(new JLabel("Endereco"));
        CreateAddress = new JTextArea();
        CreateAddress.setBorder(borderBox);
        FormFields[0].add(CreateAddress);
        FormFields[0].add(new JLabel("Plano de asssinatura"));
        CreateSignaturePlain = new JTextArea();
        CreateSignaturePlain.setBorder(borderBox);
        FormFields[0].add(CreateSignaturePlain);

        FormFields[1].setLayout(new GridLayout(6, 2));
        FormFields[1].add(new JLabel("Alterar Dados do Assinante"));
        UpdateBtn = new JButton("Alterar");
        UpdateBtn.addActionListener(new UpdateFields());
        FormFields[1].add(UpdateBtn);
        FormFields[1].add(new JLabel("Nome  para alteracao"));
        UpdatableName = new JTextArea();
        UpdatableName.setBorder(borderBox);
        FormFields[1].add(UpdatableName);
        FormFields[1].add(new JLabel("Novo Cartao"));
        UpdatateCreditCard = new JTextArea();
        UpdatateCreditCard.setBorder(borderBox);
        FormFields[1].add(UpdatateCreditCard);
        FormFields[1].add(new JLabel("Novo nome"));
        UpdateName = new JTextArea();
        UpdateName.setBorder(borderBox);
        FormFields[1].add(UpdateName);
        FormFields[1].add(new JLabel("Novo endereco"));
        UpdateAddress = new JTextArea();
        UpdateAddress.setBorder(borderBox);
        FormFields[1].add(UpdateAddress);
        FormFields[1].add(new JLabel("Novo Plano de assinatura"));
        UpdateSignaturePlain = new JTextArea();
        UpdateSignaturePlain.setBorder(borderBox);
        FormFields[1].add(UpdateSignaturePlain);

        FormFields[2].setLayout(new GridLayout(2, 2));
        DeleteBtn = new JButton("Excluir (digite o nome)");
        DeleteBtn.addActionListener(new DeleteData());
        FormFields[2].add(DeleteBtn);
        DeletableName = new JTextArea();
        DeletableName.setBorder(borderBox);
        FormFields[2].add(DeletableName);

        TabelaAssinantes = new JTable(new Object[1][5],
                new String[] { "ID", "cartao", "Nome", "Endereco", "Plano de assinatura" });
        FormFields[3].setLayout(new GridLayout(2, 1));
        FormFields[3].add(TabelaAssinantes.getTableHeader());
        FormFields[3].add(TabelaAssinantes);

        for (int i = 0; i < 4; i++)
            FormFields[i].setBorder(borderBox);
        Update();
        CreateUserInterface(FormFields);
    }

    public void Update() {
        DefaultTableModel SignatureModel = new DefaultTableModel(
                new Object[] { "Cartao", "Nome", "Endereco", "Plano de assinatura" }, 0);
        try {
            ResultSet SQLResult = SQLStatement.executeQuery("SELECT * FROM ASSINANTES");
            while (SQLResult.next()) {
                String Cartao = SQLResult.getString("CARTAO");
                String Nome = SQLResult.getString("NOME");
                String Endereco = SQLResult.getString("ENDERECO");
                String Plano = SQLResult.getString("PLANO");
                SignatureModel.addRow(new Object[] { Cartao, Nome, Endereco, Plano });
                SQLResult.next();
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
            System.exit(1);
        }
        TabelaAssinantes.setModel(SignatureModel);
    }

    class UpdateFields implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String NomeEspecificado = UpdatableName.getText();
            String Cartao = UpdatateCreditCard.getText();
            String Nome = UpdateName.getText();
            String Endereco = UpdateAddress.getText();
            String Plano = UpdateSignaturePlain.getText();

            try {
                SQLStatement.executeUpdate("UPDATE ASSINANTES SET CARTAO='" + Cartao + "', NOME='" + Nome
                        + "', ENDERCO='" + Endereco + "', PLANO='" + Plano + "' WHERE NOME='" + NomeEspecificado + "'");
            } catch (SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }

    class CreateNew implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String Nome = CreateName.getText();
            String Cartao = CreateCreditCard.getText();
            String Endereco = CreateAddress.getText();
            String Plano = CreateSignaturePlain.getText();

            try {
                SQLStatement.executeUpdate("INSERT INTO ASSINANTES VALUES('" + Cartao + "', '" + Nome + "', '"
                        + Endereco + "', '" + Plano + "')");
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
                SQLStatement.executeUpdate("DELETE FROM ASSINANTES WHERE NOME='" + Nome + "'");
            } catch (SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            Update();
        }
    }
}